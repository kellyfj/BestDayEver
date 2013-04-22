package com.nokia.scbe.hackathon.bestdayever.placesapi;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpHost;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;



/**
 * Factory for 3rd party clients.
 */
public class ClientFactoryBean {
	   private static final Logger LOG = Logger.getLogger(ClientFactoryBean.class);
		
    private HttpClient placesAPIHttpClient;

    private String placesAPIUri;

    private  static final int HTTP = 80;
    private  static final int HTTPS = 443;

    private  static final int MIN_EXECUTION_COUNT = 5;
    private  static final int MAX_EXECUTION_COUNT = 6;


    /**
     * Proxy host
     */
    private String proxyHost = null;

    /**
     * Proxy port
     */
    private int proxyPort = 0;

    static {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
    }

    public PlacesAPIClient createPlacesAPIClient() {
            return new RestProxyBuilder<PlacesAPIClient>(PlacesAPIClient.class, placesAPIUri)
                    .withClientExecutor(new ApacheHttpClient4Executor(placesAPIHttpClient))
                    .build();
    }

    public void setPbapiUri(final String placesAPIUri) {
        this.placesAPIUri = placesAPIUri;
    }


    public void setPbapiHttpClient(final HttpClient httpClient) {
        HttpParams params = httpClient.getParams();
        //if there is proxy then set it.
        if (proxyHost != null) {
            HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
            params.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
        SchemeRegistry schemeRegistry = new SchemeRegistry();

        schemeRegistry.register(new Scheme("http", HTTP, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", HTTPS, SSLSocketFactory.getSocketFactory()));
        ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(schemeRegistry);

        DefaultHttpClient httpClientWithRetryHandler = new DefaultHttpClient(connectionManager, params);
        httpClientWithRetryHandler.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {

            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= MAX_EXECUTION_COUNT) {
                    return false;
                }
                if (exception instanceof NoHttpResponseException || exception instanceof SocketTimeoutException) {
                    if (executionCount < MIN_EXECUTION_COUNT) {
                        LOG.warn("pbapiHttpClient encountered exception '" + exception.getMessage() + "' retrying request, retryCount=" + executionCount);
                    } else {
                        LOG.error("pbapiHttpClient encountered exception '" + exception.getMessage() + "' retrying request, retryCount=" + executionCount);
                    }
                    return true;
                }

                return false;
            }
        });

        this.placesAPIHttpClient = httpClientWithRetryHandler;

    }

    @Autowired
    public void setProxyHost(String proxyHost) {
      this.proxyHost = proxyHost;
      LOG.info("ClientFactoryBean>>>>>>>>>>>>>> proxyHost:"+ proxyHost);
    }

    @Autowired
    public void setProxyPort(Integer proxyPort) {
      this.proxyPort = proxyPort;
      LOG.info("ClientFactoryBean>>>>>>>>>>>>>> proxyPort:"+ proxyPort);
    }
}

package com.nokia.scbe.hackathon.bestdayever.placesapi;

import static org.apache.http.params.HttpConnectionParams.setConnectionTimeout;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

public class ClientExecutorProvider {
    private static final int MAX_HTTP_CONNECTIONS_TOTAL = 20;
    private static final int MAX_HTTP_CONNECTIONS_PER_ROUTE = 8;
    private static final int HTTP_TIMEOUT = 5000;

    private ClientExecutorProvider() {
    }

    public static ClientExecutor getDefaultInstance() {
        return getInstance(HTTP_TIMEOUT, MAX_HTTP_CONNECTIONS_PER_ROUTE, MAX_HTTP_CONNECTIONS_TOTAL);
    }

    public static ClientExecutor getInstance(int timeOut, int maxHttpConnectionsPerRoute, int maxHttpConnections) {
        HttpParams params = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(params, timeOut);
        HttpConnectionParams.setSoTimeout(params, timeOut);

        ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager();
        connectionManager.setDefaultMaxPerRoute(maxHttpConnectionsPerRoute);
        connectionManager.setMaxTotal(maxHttpConnections);
        return getInstance(params, connectionManager);
    }

    public static ClientExecutor getInstance(HttpParams params, ClientConnectionManager connectionManager) {
        DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager, params);
        ApacheHttpClient4Executor clientExecutor = new ApacheHttpClient4Executor(httpClient);
        return clientExecutor;
    }


    /*
    public static ClientExecutor getSingleConnectionExecutorHttpsAware() {
        SSLSocketFactory sf = null;
        try {
            sf = new SSLSocketFactory(new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }, new AllowAllHostnameVerifier());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", 443, sf));
        registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

        ClientConnectionManager clientConnectionManager = new SingleClientConnManager(registry);

        DefaultHttpClient httpClient = new DefaultHttpClient(clientConnectionManager);
        setConnectionTimeout(httpClient.getParams(), HTTP_TIMEOUT);
        return new ApacheHttpClient4Executor(httpClient);
    }*/

}

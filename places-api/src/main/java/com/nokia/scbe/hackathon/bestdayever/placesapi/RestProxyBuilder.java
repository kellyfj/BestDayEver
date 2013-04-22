package com.nokia.scbe.hackathon.bestdayever.placesapi;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJacksonProvider;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.Assert.notNull;


/**
 * Note this class create new resteasy provider factory and you need to register all
 * providers/exception mappers etc (which normally done with spring) manually.
 */
public class RestProxyBuilder<T> {

    private static ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();

    static {
        instance.registerProvider(ResteasyJacksonProvider.class);
        instance.registerProvider(JacksonContextResolver.class);
    }


    private String baseUrl;
    private Class<T> clazz;
    private ClientExecutor clientExecutor = ClientExecutorProvider.getDefaultInstance();
    private List<Object> prefixInterceptors = new ArrayList<Object>();

    public RestProxyBuilder(Class<T> clazz, final String baseUrl) {
        this.clazz = clazz;
        this.baseUrl = baseUrl;
    }

    public RestProxyBuilder<T> withClientExecutor(final ClientExecutor clientExecutor) {
        this.clientExecutor = clientExecutor;
        return this;
    }

    public RestProxyBuilder<T> withPrefixInterceptors(final Object... interceptors) {
        this.prefixInterceptors.addAll(Arrays.asList(interceptors));
        return this;
    }

    public T build() {
        notNull(baseUrl, "baseUrl is not provided");
        notNull(clazz, "class is not provided");

        URI baseUri = ProxyFactory.createUri(baseUrl);
        ClientRequestFactory cf = new ClientRequestFactory(clientExecutor, instance, baseUri);

        for (Object prefixInterceptor : prefixInterceptors) {
            cf.getPrefixInterceptors().registerInterceptor(prefixInterceptor);
        }

        return cf.createProxy(clazz, baseUri);
    }


}

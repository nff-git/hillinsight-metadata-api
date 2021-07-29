package hillinsight.metadata.api.server.configurations;

import focus.apiclient.core.ApiClient;
import focus.apiclient.spring.EnabledServiceNameFilter;
import focus.apiclient.spring.RestTemplateApiClient;
import focus.core.utils.ObjectUtil;
import focus.httpclient.config.HttpClientConfiguration;
import focus.spring.extensions.SpringContextUtil;
import focus.spring.extensions.web.client.CustomResponseErrorHandler;
import hillinsight.metadata.api.utils.convention.StaticVar;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class ApiClientConfig {

    @Bean(name = "focus.client.factory")
    public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public HttpClient httpClient(HttpClientConfiguration config) {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(config.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(config.getDefaultMaxPerRoute());
        connectionManager.setValidateAfterInactivity(config.getValidateAfterInactivity());
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(config.getSocketTimeout()) //服务器返回数据(response)的时间，超过抛出read timeout
                .setConnectTimeout(config.getConnectTimeout()) //连接上服务器(握手成功)的时间，超出抛出connect timeout
                .setConnectionRequestTimeout(config.getConnectionRequestTimeout())//从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }

    @Bean(StaticVar.ApiClientBeans.NORMAL_REST_TEMPLATE)
    public RestTemplate normalRestTemplate(@Qualifier("focus.client.factory") ClientHttpRequestFactory factory, HttpMessageConverters converters) {
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setMessageConverters(converters.getConverters());
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

    @Bean(StaticVar.ApiClientBeans.RIBBON_REST_TEMPLATE)
    @LoadBalanced
    public RestTemplate ribbonRestTemplate(@Qualifier("focus.client.factory") ClientHttpRequestFactory factory, HttpMessageConverters converters) {
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setMessageConverters(converters.getConverters());
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

    @Value("${apisdk.hlinkapi.address:https://open-ushu.hillinsight.tech}")
    private String hlinkApiAddress;

    @Bean(StaticVar.ApiClientKeys.HLINK_API_CLIENT)
    public ApiClient myApiClient(@Qualifier(StaticVar.ApiClientBeans.NORMAL_REST_TEMPLATE) RestTemplate normal, @Qualifier(StaticVar.ApiClientBeans.RIBBON_REST_TEMPLATE) RestTemplate ribbon) {

        String[] profile = SpringContextUtil.getApplicationContext().getEnvironment().getActiveProfiles();

        if (Arrays.stream(profile).anyMatch(v -> v.equals("local")))
            return ObjectUtil.initObject(new RestTemplateApiClient(normal))
                    .init(v -> v.setBaseAddress(this.hlinkApiAddress))
                    .getObject();
        else
            return ObjectUtil.initObject(new RestTemplateApiClient(ribbon))
                    .init(v -> v.setBaseAddress(this.hlinkApiAddress))
                    .init(v -> v.getFilters().add(new EnabledServiceNameFilter("http")))
                    .getObject();
    }

    @Value("${apisdk.acs.address:https://dev-gateway-shumei.hillinsight.tech}")
    private String acsApiAddress;

    @Bean(StaticVar.ApiClientKeys.ACS_API_CLIENT)
    public ApiClient acsApiClient(@Qualifier(StaticVar.ApiClientBeans.NORMAL_REST_TEMPLATE) RestTemplate normal, @Qualifier(StaticVar.ApiClientBeans.RIBBON_REST_TEMPLATE) RestTemplate ribbon) {

        String[] profile = SpringContextUtil.getApplicationContext().getEnvironment().getActiveProfiles();

        if (Arrays.stream(profile).anyMatch(v -> v.equals("local")))
            return ObjectUtil.initObject(new RestTemplateApiClient(normal))
                    .init(v -> v.setBaseAddress(this.acsApiAddress))
                    .getObject();
        else
            return ObjectUtil.initObject(new RestTemplateApiClient(ribbon))
                    .init(v -> v.setBaseAddress(this.acsApiAddress))
                    .init(v -> v.getFilters().add(new EnabledServiceNameFilter("http")))
                    .getObject();
    }
}


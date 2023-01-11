package com.lt.stock.config;

import com.lt.stock.pojo.HttpPoolConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @description: 定义访问http服务的配置类
 * @author: ~Teng~
 * @date: 2023/1/9 13:37
 */
@Configuration
public class HttpClientConfig {

    private HttpPoolConfig httpPoolConfig;

    public HttpClientConfig(HttpPoolConfig httpPoolConfig) {
        this.httpPoolConfig = httpPoolConfig;
    }

    @Bean
    public RestTemplate restTemplate() {
        // 构建 restTemplate 对象
        RestTemplate restTemplate = new RestTemplate();
        // 设置连接池工厂
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        return restTemplate;
    }

    /**
     * 配置http请求工厂bean
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        //获取httpClient对象
        CloseableHttpClient client =
                HttpClientBuilder.create()
                        //设置最大连接数
                        .setMaxConnTotal(httpPoolConfig.getMaxConnectionTotal())
                        //路由到单台机器的最大数量
                        .setMaxConnPerRoute(httpPoolConfig.getRouteMaxCount())
                        //空闲连接超时时间
                        .evictIdleConnections(httpPoolConfig.getConnectionIdleTimeOut(), TimeUnit.MILLISECONDS)
                        //设置失败重试次数
                        .setRetryHandler(new DefaultHttpRequestRetryHandler(httpPoolConfig.getRetryCount(), true))
                        .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                        .build();
        //构建连接对象
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(client);
        //设置连接最大超时时间
        clientHttpRequestFactory.setConnectTimeout(httpPoolConfig.getConnectionTimeOut());
        //设置读取数据超时时间
        clientHttpRequestFactory.setReadTimeout(httpPoolConfig.getReadTimeOut());
        //设置等待获取连接池超时时间
        clientHttpRequestFactory.setConnectionRequestTimeout(httpPoolConfig.getConnectionRequestTimeOut());
        //设置是否缓存请求体（如果存在大量post请求，且请求参数一致，则可设置为true）
        clientHttpRequestFactory.setBufferRequestBody(false);
        return clientHttpRequestFactory;
    }
}

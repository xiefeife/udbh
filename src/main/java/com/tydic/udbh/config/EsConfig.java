package com.last.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: xiehh
 * @Date:2020/12/2 10:56
 * @ClassName:EsConfig
 */
@Component
public class EsConfig {

    private volatile static RestHighLevelClient restHighLevelClient;


    private static String host;


    private static int port;

//    @Value("${es.host}")
    public  void setHost(String host) {
        EsConfig.host = host;
    }

//    @Value("${es.port}")
    public  void setPort(int port) {
        EsConfig.port = port;
    }


    public static RestHighLevelClient getclient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
//                        new HttpHost(host, port, "http")));
        return client;

    }

}

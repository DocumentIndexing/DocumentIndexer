package com.g4pas.index.elastic;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Elasticsearch Setup
 *
 * @author James Fawke
 */
@Configuration

public class ElasticDefinition {

    @Bean
    public RestHighLevelClient restHighLevelClient(ElasticConfig config) {
        return new RestHighLevelClient(getBuilder(config));
    }

    @Bean
    public RestClient elasticClient(ElasticConfig config) {

        RestClient client = getBuilder(config).build();
        return client;

    }

    private RestClientBuilder getBuilder(final ElasticConfig config) {
        return RestClient.builder(buildHosts(config));
    }

    private HttpHost[] buildHosts(ElasticConfig config) {
        return config.getHttpAddresses()
                     .stream()
                     .map(cfg -> new HttpHost(cfg.getHost(),
                                              cfg.getPort(),
                                              cfg.getScheme()))
                     .toArray(HttpHost[]::new);

    }


}

package com.g4pas.index.elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by James Fawke on 01/02/2017.
 */
@Component
public class ElasticRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticRepository.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Map<String, String> emptyMap = Collections.emptyMap();

    @Autowired
    private RestHighLevelClient elasticClient;


    RestHighLevelClient getElasticClient() {
        return elasticClient;
    }

    ElasticRepository setElasticClient(final RestHighLevelClient elasticClient) {
        LOGGER.info("setElasticClient([elasticClient]) : with {}", elasticClient);
        this.elasticClient = elasticClient;
        return this;
    }


    public boolean insertData(String index, String type, Map<String, Object> document) {
        final IndexRequest source = new IndexRequest(index,
                                                     type);
        source.source(document);
        IndexResponse indexResponse = null;
        try {
            LOGGER.info("insertData([index, type, document]) : source ");
            indexResponse = getElasticClient().index(source,
                                                     RequestOptions.DEFAULT);
            LOGGER.info("insertData([index, type, document]) : document #{} created",
                        indexResponse.getId());
        } catch (IOException e) {
            LOGGER.error("insertData([id, index, type, document, version]) : failed to index Error Message",
                         e,
                         e);
        }
        return (null != indexResponse
                && RestStatus.OK.equals(indexResponse.status()));

    }

}

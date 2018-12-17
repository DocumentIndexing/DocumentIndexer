package com.g4pas.index.elastic;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Test.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElasticRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticRepositoryTest.class);

    @Mock
    private RestHighLevelClient client;

    @Mock
    private IndexResponse response;

    private ElasticRepository repo = new ElasticRepository();

    @Before
    public void init() {
        repo.setElasticClient(client);
    }


    @Test
    public void insertData() throws Exception {
        final String type = "testType";
        final String index = "testIndex";
        final String id = "testId";
        final Map<String, Object> source = new HashMap<>();
        source.put("title",
                   "testTitle");

        LOGGER.info("insertData([]) : client is {} ",
                    client);
        when(client.index(any(IndexRequest.class),
                          eq(RequestOptions.DEFAULT)
                         )
            ).thenReturn(response);

        when(response.status()).thenReturn(RestStatus.OK);

        repo.insertData(index,
                        type,
                        source);
    }

}
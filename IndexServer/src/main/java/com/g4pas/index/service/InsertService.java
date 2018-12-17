package com.g4pas.index.service;

import com.g4pas.index.model.request.InsertDocumentRequest;
import com.g4pas.index.elastic.ElasticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * This service will insert the document to ensure that the enriched version is updated into elastic store
 */
@Service

public class InsertService {
    private static final AtomicLong counter = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(InsertService.class);
    @Autowired
    private ElasticRepository repo;

    public ElasticRepository getRepo() {
        return repo;
    }

    public InsertService setRepo(final ElasticRepository repo) {
        this.repo = repo;
        return this;
    }

    /**
     * Called by Spring Integration when a new Page is and has been enriched.
     *
     * @param request
     * @return
     */
    public boolean insert(InsertDocumentRequest request)  {

        final boolean b = getRepo().insertData(request.getIndex(),
                                               request.getType(),
                                               request.getPayload()
                                                      .getDocument());
        LOGGER.info("insert(InsertDocumentRequest request) :{} documents  inserted ",
                    counter.incrementAndGet());
        return b;
    }
}

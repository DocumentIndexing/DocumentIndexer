package com.g4pas.index.service;

import com.g4pas.index.model.ModelConstants;
import com.g4pas.index.model.request.InsertDocumentRequest;
import com.g4pas.index.service.util.IndexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Enrichment Service that takes a Request and can manipulate and add items to it, to add items that may need
 * to be derived. (such as Document Type or Index)
 * *
 *
 * @author James Fawke
 * @since 0.0.1
 */
@Service
public class EnrichmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichmentService.class);

    /**
     * Called by Spring Integration when a new Document Data request is provided
     *
     * @param request
     * @return
     */
    public InsertDocumentRequest enrichDocument(final InsertDocumentRequest request) {

        request.setIndex(IndexUtil.deriveItemsForSaleIndexName(request.getPayload()));
        request.setType(ModelConstants.BOOKS_FOR_SALE_DOC_TYPE);
        return request;
    }
}

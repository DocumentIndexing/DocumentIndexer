package com.g4pas.index.service.util;

import com.g4pas.index.model.ModelConstants;
import com.g4pas.index.model.payload.DocumentPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class IndexUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexUtil.class);

    private IndexUtil() {
        // DO NOT INSTANTIATE
    }

    public static String deriveItemsForSaleIndexName(DocumentPayload payload) {

        Date publishDate = new Date();
        final Object postedDate = payload.getDocument()
                                         .get(ModelConstants.POSTED_DATE_FIELD);
        if (postedDate instanceof Date) {
            LOGGER.info("deriveItemsForSaleIndexName([payload]) : dated: {}",
                        postedDate);
            publishDate = (Date) postedDate;
        } else {
            LOGGER.warn("deriveItemsForSaleIndexName([payload]) : posted date not found in document");
        }
        return String.format(ModelConstants.BOOKS_FOR_SALE_INDEX_NAME_FORMAT,
                             publishDate);
    }
}

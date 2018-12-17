package com.g4pas.index.service.util;

import com.g4pas.index.model.payload.ItemForSalePayload;
import com.g4pas.index.model.ModelConstants;
import com.g4pas.index.model.payload.DocumentPayload;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class IndexUtilTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexUtilTest.class);
    @Test
    public void deriveItemsForSaleIndexName() {
        final Calendar publishCalendar = Calendar.getInstance();
        publishCalendar.set(2017,
                            1,
                            1);
        final Date publishedDate = publishCalendar.getTime();
        DocumentPayload p = new ItemForSalePayload().setDocument(Map.of(ModelConstants.POSTED_DATE_FIELD, publishedDate));
        final String indexName = IndexUtil.deriveItemsForSaleIndexName(p);
        LOGGER.info("deriveItemsForSaleIndexName([]) : indexName {}",indexName);
        Assert.assertEquals("books-2017-02", indexName);
    }
}
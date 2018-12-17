package com.g4pas.index.model.payload;

import java.util.Map;

/**
 * Created by fawks on 15/02/2017.
 */
public class ItemForSalePayload implements DocumentPayload{


    private Map<String, Object> document;

    public Map<String, Object> getDocument() {
        return document;
    }

    public ItemForSalePayload setDocument(final Map<String, Object> document) {
        this.document = document;
        return this;
    }
}

package com.g4pas.index.model.request;

import com.g4pas.index.model.payload.DocumentPayload;

public class InsertDocumentRequest implements Request {
    private DocumentPayload payload;
    private String index;
    private String type;

    public DocumentPayload getPayload() {
        return payload;
    }

    public InsertDocumentRequest setPayload(
            final DocumentPayload payload) {
        this.payload = payload;
        return this;
    }

    public String getIndex() {
        return index;
    }

    public InsertDocumentRequest setIndex(final String index) {
        this.index = index;
        return this;
    }

    public String getType() {
        return type;
    }

    public InsertDocumentRequest setType(final String type) {
        this.type = type;
        return this;
    }
}

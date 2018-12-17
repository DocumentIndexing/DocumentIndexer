package com.g4pas.index.model.payload;

import java.io.IOException;
import java.util.Map;

public class DocumentPayloadFactory {
    public static DocumentPayload getInstance(Map json) throws IOException {
        //Currently only one type of request on the queue
        return new ItemForSalePayload().setDocument(json);
    }
}

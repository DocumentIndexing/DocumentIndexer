package com.g4pas.index.model.request;

import com.g4pas.index.exception.TransformServiceException;
import com.g4pas.index.model.payload.DocumentPayload;
import com.g4pas.index.model.payload.DocumentPayloadFactory;
import com.g4pas.index.service.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RequestFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFactory.class);

    public static Request getInstance(String json) throws TransformServiceException {
        //Currently only one type of request on the queue

        InsertDocumentRequest insertDocumentRequest = null;
        if (StringUtils.isNotBlank(json)) {
            try {

                final DocumentPayload instance = DocumentPayloadFactory.getInstance(JsonUtil.toMap(json));
                insertDocumentRequest = new InsertDocumentRequest().setPayload(instance);
            } catch (IOException e) {

                String.format("Failed to generate Map  from %s, caused the following error message",
                              json,
                              e.getMessage());
                LOGGER.error("getInstance([json]) :  ",
                             json,
                             e);
                throw new TransformServiceException(e);
            }
        }
        return insertDocumentRequest;

    }

}


package com.g4pas.index.service;

import com.g4pas.index.exception.TransformServiceException;
import com.g4pas.index.model.request.Request;
import com.g4pas.index.model.request.RequestFactory;
import org.springframework.stereotype.Service;

/**
 * Transforms the Json String into the inbound message request instance.<br/>
 * This was originally in the DSL <code>.transform()</code> but the exception was not being handled correctly, so,
 * I have extract in to a Service component
 */
@Service
public class TransformService {

    public Request transform(String json) throws TransformServiceException {
        return RequestFactory.getInstance(json);
    }
}

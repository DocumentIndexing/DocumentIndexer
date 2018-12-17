package com.g4pas.index.service;

import com.g4pas.index.elastic.ElasticRepository;
import com.g4pas.index.model.payload.DocumentPayload;
import com.g4pas.index.model.payload.ItemForSalePayload;
import com.g4pas.index.model.request.InsertDocumentRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InsertServiceTest {

    @Mock
    ElasticRepository repo;

    @Test
    public void insert() throws Exception {
        InsertDocumentRequest document = new InsertDocumentRequest();
        String testIndex = "testIndex";
        String testType = "testType";
        final HashMap<String, Object> testDocument = new HashMap<>();
        DocumentPayload testPayload = new ItemForSalePayload().setDocument(testDocument);


        document.setIndex(testIndex)
                .setType(testType)
                .setPayload(testPayload);


        when(repo.insertData(eq(testIndex),
                             eq(testType),
                             eq(testDocument)))
                .thenReturn(true);

        new InsertService().setRepo(repo)
                           .insert(document);
        Mockito.verify(repo)
               .insertData(testIndex,
                           testType,
                           testDocument);
    }
}
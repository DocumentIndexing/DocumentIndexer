package com.g4pas.index.service;

import com.beust.jcommander.internal.Lists;
import com.g4pas.index.model.payload.DocumentPayload;
import com.g4pas.index.model.payload.ItemForSalePayload;
import com.g4pas.index.model.request.InsertDocumentRequest;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Test.
 */
public class EnrichmentServiceTest {

  EnrichmentService service = new EnrichmentService();

  @Test
  public void testEnrichInsertDocumentRequest() throws IOException {


    final HashMap<String, Object> document = new HashMap<>();
    document.put("publishDate",new Date());
    DocumentPayload payload = new ItemForSalePayload().setDocument(document);
    InsertDocumentRequest request = new InsertDocumentRequest().setPayload(payload);

    List<String> testContent = Lists.newArrayList("TestContent");


//TODO
//    Document enrichedDocument = service.enrichDocument(request);
//    assertEquals(testContent,
//                 enrichedDocument.getSource()
//                                 .get(PAGEDATA.getIndexDocProperty()));
  }

}
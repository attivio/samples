/**
 * Copyright 2014 Attivio Inc., All rights reserved.
 */
package com.acme;

import java.util.Date;
import java.util.List;

import com.attivio.model.AttivioException;
import com.attivio.model.document.ResponseDocument;
import com.attivio.model.document.ResponseDocumentList;
import com.attivio.model.request.QueryFeedback;
import com.attivio.model.request.QueryResponseInfo;
import com.attivio.sdk.server.component.query.ResponseTransformer;

/** Sample response transformer that adds a new document to the results. */
public class SampleResponseTransformer implements ResponseTransformer {

  @Override
  public void processResponseInfo(QueryResponseInfo info) throws AttivioException {
    // you can modify the QueryResponseInfo here if necessary
    List<QueryFeedback> qfList = info.getFeedbackByMessageName("sampleProcessedResponse");
    QueryFeedback qf = new QueryFeedback(this.getClass().getSimpleName(), "sampleProcessedResponseInfo",
        "also added an additional feedback message to the query's QueryResponseInfo");
    qfList.add(qf);
    info.setFeedback(qfList);
  }

  @Override
  public void processResponseDocuments(QueryResponseInfo info, ResponseDocumentList documents) throws AttivioException {
    // here we'll add an additional response document
    ResponseDocument doc4 = new ResponseDocument("4");
    doc4.setField("title", "document 4");
    doc4.setField("cat", "cat1");
    doc4.setField("date", new Date());
    documents.add(doc4);
    // adding feedback is optional but is useful for letting end users know what happened.
    info.addFeedback(this.getClass().getSimpleName(), "sampleProcessedResponse", "added a new response document");
  }

}

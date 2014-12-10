/**
 * Copyright 2014 Attivio Inc., All rights reserved.
 */
package com.acme;

import java.util.ArrayList;
import java.util.List;

import com.attivio.messages.QueryRequest;
import com.attivio.model.AttivioException;
import com.attivio.model.request.QueryFeedback;
import com.attivio.sdk.server.annotation.ConfigurationOption;
import com.attivio.sdk.server.component.query.QueryTransformer;

/** Sample query transformers that adds a new field for faceting. */
public class SampleQueryTransformer implements QueryTransformer {

  private String facetField = "myfacetfield";

  @Override
  public List<QueryFeedback> processQuery(QueryRequest query) throws AttivioException {
    query.addFacetField(facetField);
    // adding feedback is optional but is useful for letting end users know what happened.  Return null if there is no feedback
    List<QueryFeedback> feedback = new ArrayList<QueryFeedback>();
    feedback.add(new QueryFeedback(this.getClass().getSimpleName(), "sample", "added a facet field: " + facetField));
    return feedback;
  }

  @ConfigurationOption(displayName = "Facet Field", description = "Field to add a facet request for to each query request")
  public String getFacetField() {
    return facetField;
  }

  public void setFacetField(String facetField) {
    this.facetField = facetField;
  }

}

/**
 * Copyright 2014 Attivio Inc., All rights reserved.
 */
package com.acme;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.attivio.TestUtils;
import com.attivio.bus.PsbProperties;
import com.attivio.model.AttivioException;
import com.attivio.model.document.AttivioDocument;
import com.attivio.model.document.Field;
import com.attivio.model.document.FieldValue;

public class SampleAdvancedIngestTransformerTest {

  private String inputField = "testin";
  private String outputField = "testout";

  @BeforeClass
  public static void initializeTestEnvironment() throws AttivioException, IOException {
    PsbProperties.setProperty("log.printStackTraces", true);
    PsbProperties.setProperty("log.level", "INFO");
    PsbProperties.setProperty("attivio.project", System.getProperty("user.dir"));
    PsbProperties.setProperty("log.directory", System.getProperty("user.dir") + "/build/logs");
    PsbProperties.setProperty("data.directory", System.getProperty("user.dir") + "/build/data");
    TestUtils.initializeEnvironment();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testTransformer() throws AttivioException {
    // Initialize a transformer object
    SampleAdvancedIngestTransformer xformer = new SampleAdvancedIngestTransformer();
    Map<String, String> tmpMap = new HashMap<String, String>();
    tmpMap.put(inputField, outputField);
    xformer.setFieldMapping(tmpMap);
    AttivioDocument doc = new AttivioDocument("doc0001");
    doc.setField(inputField, "THIS IS A SAMPLE UPPER CASE DOCUMENT.");
    Field<Object> f = (Field<Object>) doc.getField(inputField);

    for (FieldValue<?> fv : f) {
      String tmp = xformer.createMappedValue(inputField, fv);
      String outF = xformer.getFieldMapping().get(inputField);
      if (outF != null) {
        doc.addToField(outF, tmp);
      }
    }

    // print the document to see what it looks like
    System.err.println(doc.toString());

    // Grab the text field value after transformation
    String processedTextValue = doc.getFirstValueAsString(outputField);

    // Assert that the document's text field is now all in lower case
    Assert.assertEquals("this is a sample upper case document.", processedTextValue);

  }

}

/**
 * Copyright 2014 Attivio Inc., All rights reserved.
 */
package com.acme;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.attivio.TestUtils;
import com.attivio.bus.PsbProperties;
import com.attivio.model.AttivioException;
import com.attivio.model.document.AttivioDocument;
import com.attivio.test.TransformerTestUtils;

/** The simplest unit test of a document transformer. */
public class SampleSimpleIngestTransformerTest {

  @BeforeClass
  public static void initializeTestEnvironment() throws AttivioException, IOException {
    PsbProperties.setProperty("log.printStackTraces", true);
    PsbProperties.setProperty("log.level", "INFO");
    PsbProperties.setProperty("attivio.project", System.getProperty("user.dir"));
    PsbProperties.setProperty("log.directory", System.getProperty("user.dir") + "/build/logs");
    PsbProperties.setProperty("data.directory", System.getProperty("user.dir") + "/build/data");
    TestUtils.initializeEnvironment();
  }

  @Test
  public void testTransformer() throws AttivioException {
    // Initialize a transformer object
    SampleSimpleIngestTransformer xformer = new SampleSimpleIngestTransformer();
    xformer.setField("foo");
    xformer.setValue("bar");
    TransformerTestUtils.startTransformer(xformer);

    AttivioDocument doc = new AttivioDocument("1234");
    xformer.processDocument(doc);

    Assert.assertEquals("bar", doc.getFirstValue("foo"));
  }

}

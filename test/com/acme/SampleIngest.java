/**
 * Copyright 2014 Attivio Inc., All rights reserved.
 */
package com.acme;

import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.attivio.TestUtils;
import com.attivio.bus.PsbProperties;
import com.attivio.messages.DocumentList;
import com.attivio.model.AttivioException;
import com.attivio.model.document.AttivioDocument;
import com.attivio.model.query.QueryString;
import com.attivio.sdk.client.AieClientFactory;
import com.attivio.sdk.client.DefaultAieClientFactory;
import com.attivio.sdk.client.IngestClient;

/** Sample search code to run against a default empty Attivio installation. */
public class SampleIngest {

  // Change these to point to your Attivio installation
  private String host = "localhost";
  private int port = 17000;
  private final AieClientFactory clientFactory = new DefaultAieClientFactory();

  @BeforeClass
  public static void initializeTestEnvironment() throws AttivioException, IOException {
    PsbProperties.setProperty("log.printStackTraces", true);
    PsbProperties.setProperty("log.level", "INFO");
    PsbProperties.setProperty("attivio.project", System.getProperty("user.dir"));
    PsbProperties.setProperty("log.directory", System.getProperty("user.dir") + "/build/logs");
    PsbProperties.setProperty("data.directory", System.getProperty("user.dir") + "/build/data");
    TestUtils.initializeEnvironment();
  }

  /** Test some basic ingest logic. */
  @Test
  public void ingest() throws AttivioException {
    try {
      // create an attivio document feeder client
      // hold onto this feeder for the life of the application
      IngestClient feeder = clientFactory.createIngestClient(host, port);

      // set the default workflow
      feeder.setIngestWorkflowName("ingest");

      // create an attivio document for indexing
      AttivioDocument doc1 = new AttivioDocument("1");
      doc1.setField("title", "document 1");
      doc1.setField("cat", "cat1", "cat2", "cat3");
      doc1.setField("date", new Date());
      doc1.addToField("author", "John Doe");
      doc1.addToField("author", "Mike Smith");

      AttivioDocument doc2 = new AttivioDocument("2");
      doc2.setField("title", "document 2");
      doc2.setField("cat", "cat2");
      doc2.setField("date", new Date());

      AttivioDocument doc3 = new AttivioDocument("3");
      doc3.setField("title", "document 3");
      doc3.setField("cat", "cat3", "cat5");
      doc3.setField("date", new Date());

      // now add the docs to a single batch
      DocumentList docs = new DocumentList();
      docs.add(doc1);
      docs.add(doc2);
      docs.add(doc3);

      // feed the documents to the system and wait for completion
      feeder.feed(docs);
      feeder.waitForCompletion();

      // Assert that the expected number of documents were fed
      long docsFed = feeder.getDocumentsFed();
      Assert.assertEquals("Assertion failed on number of documents fed", 3, docsFed);

      // commit the documents to the index
      feeder.commit();

      // wait for everything to be indexed on the default workflows for 10 seconds
      feeder.waitForCompletion(10L * 1000);

      // other examples
      // note that a commit() must be called in order to make any changes visible to searches

      // feed a single document
      feeder.feed(new AttivioDocument("1234"));

      // delete a single document by document id
      feeder.delete("1");

      // delete a set of documents via a search workflow that match a query
      feeder.deleteByQuery("search", new QueryString("*:*", "advanced"));

      // don't commit changes so later tests can reuse the sample index
      //client.commit();

      // wait for the processing to complete
      feeder.waitForCompletion();

      // it's important to shut things down properly
      feeder.disconnect();

      System.out.println("Ingestion completed successfully.");

    } catch (AttivioException e) {
      // AttivioExceptions have error codes with categories and error numbers.
      // ALl the relevant documentation can be found in the ErrorCode class's javadocs.
      System.err.println("Category: " + e.getErrorCode().getCategory());
      System.err.println("Code: " + e.getErrorCode().getCode());

      // the category + code are in the stack trace also.
      e.printStackTrace();

      throw e;
    }
  }
}

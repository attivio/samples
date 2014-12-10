/**
 * Copyright 2014 Attivio Inc., All rights reserved.
 */
package com.acme;

import com.attivio.sdk.client.MessageResultListener;
import com.attivio.sdk.model.ReadOnlyDocumentResult;
import com.attivio.sdk.model.ReadOnlyMessageResult;

public class SampleMessageResultListener implements MessageResultListener {

  @Override
  public void handleDocumentResult(ReadOnlyDocumentResult docResult) {
    System.err.printf("Got result %s from %s for doc %s%n", docResult.getCode(), docResult.getStageName(), docResult.getDocId());
  }

  @Override
  public void handleMessageResult(ReadOnlyMessageResult mres) {
    System.err.printf("Got result %s from %s for message type %s%n", mres.getCode(), mres.getStageName(), mres.getMessageClass().getName());
  }

  @Override
  public void init() {
    System.err.println("Starting message result listener");
  }

  @Override
  public void shutdown() {
    System.err.println("Shutting down message result listener");
  }

}

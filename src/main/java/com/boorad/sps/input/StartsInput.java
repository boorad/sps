package com.boorad.sps.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class StartsInput<T> implements Input<T> {

  private Logger LOG = LoggerFactory.getLogger(StartsInput.class);

  private final String url;
  protected BufferedReader reader;

  public StartsInput(String url) {
    this.url = url;
  }

  public boolean open() {
    try {
      URL url = new URL(this.url);
      reader = new BufferedReader(new InputStreamReader(url.openStream()));
      LOG.info("Starting stream processing");
      return true;
    } catch( IOException e ) {
      LOG.info("Error opening Starts stream: " + e.getMessage());
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  public T nextRecord() {
    try {
      String msg = reader.readLine();
      if (msg == null || msg.equals("") || msg.isEmpty()) {
        return null;
      }
      msg = msg.trim();
      return (T)msg;
    } catch( IOException e ) {
      LOG.info("Error retrieving Starts message: " + e.getMessage());
    }
    return null;
  }

  public void close() {
    try {
      reader.close();
    } catch( IOException e ) {
      LOG.info("Error closing Starts stream: " + e.getMessage());
    }
  }

}

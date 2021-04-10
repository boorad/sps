package com.boorad.sps.operator;

import com.boorad.sps.message.StartsMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartsJsonDeserOperator {

  private final Logger LOG = LoggerFactory.getLogger(StartsJsonDeserOperator.class);

  private final Gson gson;

  public StartsJsonDeserOperator() {
    this.gson = new Gson();
  }

  public StartsMessage process(String input) {
    if( input.startsWith("data: ") ) {
      String msg = input.substring(6);
      try {
        return gson.fromJson(msg, StartsMessage.class);
      } catch( JsonSyntaxException e ) {
        LOG.info("Error deserializing Starts message: " + e.getMessage());
      }
    }
    return null;
  }

}

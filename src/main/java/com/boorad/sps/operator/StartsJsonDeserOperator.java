package com.boorad.sps.operator;

import com.boorad.sps.message.StartsMessage;
import com.boorad.sps.stream.Stream;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartsJsonDeserOperator extends Operator<String, StartsMessage> {

  private final Logger LOG = LoggerFactory.getLogger(StartsJsonDeserOperator.class);

  private final Gson gson;

  public StartsJsonDeserOperator(Stream<String> inStream, Stream<StartsMessage> outStream) {
    super(inStream, outStream);
    this.gson = new Gson();
  }

  @Override
  public void process(String input, Stream<StartsMessage> outStream) {
    if( input.startsWith("data: ") ) {
      String msg = input.substring(6);
      try {
        StartsMessage newMsg = gson.fromJson(msg, StartsMessage.class);
        outStream.add(newMsg);
      } catch( JsonSyntaxException e ) {
        LOG.info("Error deserializing Starts message: " + e.getMessage());
      }
    }
  }

}

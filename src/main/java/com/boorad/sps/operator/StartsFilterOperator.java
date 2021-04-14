package com.boorad.sps.operator;

import com.boorad.sps.message.StartsMessage;
import com.boorad.sps.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartsFilterOperator extends Operator<StartsMessage, StartsMessage>{

  private final Logger LOG = LoggerFactory.getLogger(StartsFilterOperator.class);

  protected StartsMessage newMsg;

  public StartsFilterOperator(Stream<StartsMessage> inStream, Stream<StartsMessage> outStream) {
    super(inStream, outStream);
  }

  public void process(StartsMessage input, Stream<StartsMessage> outStream) {
    // TODO: maybe handle input.title.startsWith("busted data:") ??
    if( input.sev.equals("success") ) {
      newMsg = input; // set this for testing
      outStream.add(newMsg);
    }
  }

}

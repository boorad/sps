package com.boorad.sps.operator;

import com.boorad.sps.message.StartsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartsFilterOperator {

  private final Logger LOG = LoggerFactory.getLogger(StartsFilterOperator.class);

  public StartsFilterOperator() {}

  public StartsMessage process(StartsMessage input) {
    if( input.sev.equals("success") ) {
      return input;
    }
    // TODO: handle input.title.startsWith("busted data:")
    return null;
  }

}

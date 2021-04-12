package com.boorad.sps.operator;

import com.boorad.sps.message.StartsAggMessage;
import com.boorad.sps.message.StartsMessage;
import com.boorad.sps.stream.Stream;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class StartsWindowOperator extends Operator<StartsMessage, StartsAggMessage> {

  private long windowSize;
  private long lateDataWindowSize;
  private ConcurrentHashMap<Long, ArrayList<StartsAggMessage>> windows;

  public StartsWindowOperator(Stream<StartsMessage> inStream, Stream<StartsAggMessage> outStream, long windowSize, long lateDataWindowSize) {
    super(inStream, outStream);
    this.windowSize = windowSize;
    this.lateDataWindowSize = lateDataWindowSize;
    windows = new ConcurrentHashMap<>();
  }

  public void process(StartsMessage input, Stream<StartsAggMessage> outStream) {
    // mod input.time by windowSize, to get proper bucket slug
    long slug = input.time % this.windowSize;

    // add to arraylist of the hash map for appropriate Key
    //   but only if it is not past the lateData threshold


  }

  private void init() {
    // start the timer/loop that will reap windows past the lateData threshold

  }

}

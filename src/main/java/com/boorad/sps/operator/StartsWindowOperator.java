package com.boorad.sps.operator;

import com.boorad.sps.message.StartsAggMessage;
import com.boorad.sps.message.StartsMessage;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class StartsWindowOperator {

  private long windowSize;
  private long lateDataWindowSize;
  private ConcurrentHashMap<Long, ArrayList<StartsAggMessage>> windows;

  public StartsWindowOperator(long windowSize, long lateDataWindowSize) {
    this.windowSize = windowSize;
    this.lateDataWindowSize = lateDataWindowSize;
    windows = new ConcurrentHashMap<>();
    start();
  }

  // TODO: this is where simple Operators break down, and we will need Stream
  //       objects to write back to stream
  public StartsAggMessage process(StartsMessage input) {
    // mod input.time by 1000, or whatever, to get it to 1 sec buckets

    // add to arraylist of the hash map for appropriate Key
    //   but only if it is not past the lateData threshold


    return null;
  }

  private void start() {
    // start the timer/loop that will reap windows past the lateData threshold

  }

}

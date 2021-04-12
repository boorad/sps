package com.boorad.sps.operator;

import com.boorad.sps.message.StartsAggMessage;
import com.boorad.sps.message.StartsMessage;
import com.boorad.sps.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StartsWindowOperator extends Operator<StartsMessage, StartsAggMessage> {

  private Logger LOG = LoggerFactory.getLogger(StartsWindowOperator.class);

  private long windowSize;
  private long latestWindow = -1L;
  private ConcurrentHashMap<Long, ArrayList<StartsAggMessage>> windows;

  public StartsWindowOperator(Stream<StartsMessage> inStream, Stream<StartsAggMessage> outStream, long windowSize) {
    super(inStream, outStream);
    this.windowSize = windowSize;
    windows = new ConcurrentHashMap<>();
  }

  public void process(StartsMessage input, Stream<StartsAggMessage> outStream) {
    long slug = getWindowSlug(input.time);
    if( slug != latestWindow ) {
      if( latestWindow > 0L ) {
        // emit agg message to output stream and clean up windows map
        ArrayList<StartsAggMessage> msgs = windows.get(latestWindow);
        //LOG.info("need to emit slug: " + latestWindow + " with " + msgs.size() + " msgs.");
        ArrayList<StartsAggMessage> aggs = reduce(msgs);
        aggs.forEach(outStream::add);
      }

      // set latest window to new slug
      latestWindow = slug;
    }

    // add to arraylist of the hash map for appropriate Key
    ArrayList<StartsAggMessage> msgs = windows.get(slug);
    if( msgs == null || msgs.isEmpty() ) {
      msgs = new ArrayList<>();
    }
    msgs.add(getNewAggMsg(slug, input));
    windows.put(slug, msgs);

  }

  /**
   *  get proper bucket slug
   */
  private long getWindowSlug(long time) {
    return Math.floorDiv(time, this.windowSize) * this.windowSize;
  }

  /**
   * create a new aggregate message from incoming starts message
   *
   * TODO: easier w/ ctor?
   */
  private StartsAggMessage getNewAggMsg(Long window, StartsMessage msg) {
    StartsAggMessage newMsg = new StartsAggMessage();
    newMsg.window = window;
    newMsg.device = msg.device;
    newMsg.title = msg.title;
    newMsg.country = msg.country;
    newMsg.sps = 1L;
    return newMsg;
  }

  /**
   * group messages by title, device, and country, summing the sps metric
   */
  private ArrayList<StartsAggMessage> reduce(ArrayList<StartsAggMessage> msgs) {
    TreeMap<StartsAggMessage, Long> collect = msgs
      .stream()
      .collect(
        Collectors.groupingBy(
          Function.identity(),
          () -> new TreeMap<>(
            Comparator.<StartsAggMessage, String>comparing(msg -> msg.title).thenComparing(msg -> msg.device).thenComparing(msg -> msg.country)
          ),
          Collectors.summingLong(msg -> msg.sps)
        )
      );

    ArrayList<StartsAggMessage> ret = new ArrayList<>();
    collect
      .forEach((msg, sum) -> {
        msg.sps = sum;
        ret.add(msg);
      });
    return ret;
  }

}

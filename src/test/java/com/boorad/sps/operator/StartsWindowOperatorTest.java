package com.boorad.sps.operator;

import com.boorad.sps.message.StartsAggMessage;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class StartsWindowOperatorTest extends StartsOperatorTest {

  // TODO: test the actual window operation

  @Test
  public void testGetWindowSlugHappy() {
    StartsWindowOperator windowOperator = new StartsWindowOperator(filterStream, windowStream, 1000L);
    long slug = windowOperator.getWindowSlug(1515445354624L);
    assertEquals(slug, 1515445354000L);
  }

  @Test(expected = AssertionError.class)
  public void testGetWindowSlugDivideByZero() {
    @SuppressWarnings("unused")
    StartsWindowOperator windowOperator = new StartsWindowOperator(filterStream, windowStream, 0L);
  }

  @Test
  public void testReduce() {
    StartsWindowOperator windowOperator = new StartsWindowOperator(filterStream, windowStream, 1000L);

    // populate data
    ArrayList<StartsAggMessage> l = new ArrayList<>();
    l.add(getAggMsg("xbox_one", "cuervos", "BR"));
    l.add(getAggMsg("xbox_360", "narcos", "IND"));
    l.add(getAggMsg("ios", "daredevil", "BR"));
    l.add(getAggMsg("ios", "daredevil", "BR"));

    ArrayList<StartsAggMessage> results = windowOperator.reduce(l);
    results.forEach(result -> {
      switch (result.device) {
        case "xbox_one":
          assertEquals(result.sps, Long.valueOf(1L));
          break;
        case "ios":
          assertEquals(result.sps, Long.valueOf(2L));
          break;
        case "xbox_360":
          assertEquals(result.sps, Long.valueOf(1L));
          break;
      }
    });
  }

  private StartsAggMessage getAggMsg(String device, String title, String country) {
    StartsAggMessage newMsg = new StartsAggMessage();
    newMsg.window = 1000L;
    newMsg.device = device;
    newMsg.title = title;
    newMsg.country = country;
    newMsg.sps = 1L;
    return newMsg;
  }

}

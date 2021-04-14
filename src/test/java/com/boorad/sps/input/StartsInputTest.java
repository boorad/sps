package com.boorad.sps.input;

import com.boorad.sps.TestUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class StartsInputTest {

  StartsInput<String> input = new StartsInput<>("dummy.url");

  @Test
  public void testBadUrlOpen() {
    boolean result = input.open();
    assertFalse(result);
  }

  @Test
  public void testSampleData() throws IOException {
    input.reader = TestUtils.getReader();
    String line = input.nextRecord();
    assertEquals(line, "data: {\"device\":\"xbox_one\",\"sev\":\"error\",\"title\":\"orange is the new black\",\"country\":\"IND\",\"time\":1515445354624}");
    line = input.nextRecord(); // first two are dupes, test third
    line = input.nextRecord();
    assertEquals(line, "data: {\"device\":\"xbox_one_s\",\"sev\":\"success\",\"title\":\"cuervos\",\"country\":\"BR\",\"time\":1515445354624}");
  }

}

package com.boorad.sps.operator;

import com.boorad.sps.message.StartsMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class StartsJsonDeserOperatorTest extends StartsOperatorTest {

  @Test
  public void testGoodJsonMessage() {
    String goodJson = "data: {\"device\":\"xbox_one\",\"sev\":\"error\",\"title\":\"orange is the new black\",\"country\":\"IND\",\"time\":1515445354624}";
    StartsMessage goodMsg = getGoodMsg();
    when(inputStream.remove())
      .thenReturn(goodJson);
    deserOperator.process(goodJson, deserStream);
    // TODO: use hamcrest samePropertyValuesAs or similar, not toString()
    assertEquals(deserOperator.newMsg.toString(), goodMsg.toString());
  }

  @Test
  public void testBadJsonMessage() {
    String badJson = "data: <message>way worse than json</message>";
    deserOperator.newMsg = null;
    when(inputStream.remove())
      .thenReturn(badJson);
    deserOperator.process(badJson, deserStream);
    assertNull(deserOperator.newMsg);
  }

  private StartsMessage getGoodMsg() {
    StartsMessage msg = new StartsMessage();
    msg.device = "xbox_one";
    msg.sev = "error";
    msg.title = "orange is the new black";
    msg.country = "IND";
    msg.time = 1515445354624L;
    return msg;
  }

}

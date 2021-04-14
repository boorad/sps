package com.boorad.sps.operator;

import com.boorad.sps.message.StartsMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class StartsFilterOperatorTest extends StartsOperatorTest {

  @Test
  public void testSuccessMessage() {
    StartsMessage msg = getMsg("success");
    when(deserStream.remove())
      .thenReturn(msg);
    filterOperator.process(msg, filterStream);
    // TODO: use hamcrest samePropertyValuesAs or similar, not toString()
    assertEquals(filterOperator.newMsg.toString(), msg.toString());
  }

  @Test
  public void testErrorMessage() {
    StartsMessage msg = getMsg("error");
    filterOperator.newMsg = null;
    when(deserStream.remove())
      .thenReturn(msg);
    filterOperator.process(msg, filterStream);
    assertNull(filterOperator.newMsg);
  }

  private StartsMessage getMsg(String sev) {
    StartsMessage msg = new StartsMessage();
    msg.sev = sev;
    return msg;
  }

}

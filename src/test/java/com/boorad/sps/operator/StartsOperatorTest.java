package com.boorad.sps.operator;

import com.boorad.sps.message.StartsAggMessage;
import com.boorad.sps.message.StartsMessage;
import com.boorad.sps.stream.Stream;
import org.mockito.Mockito;

public abstract class StartsOperatorTest {

  @SuppressWarnings("unchecked")
  Stream<String> inputStream = (Stream<String>) Mockito.mock(Stream.class);
  @SuppressWarnings("unchecked")
  Stream<StartsMessage> deserStream = (Stream<StartsMessage>) Mockito.mock(Stream.class);
  @SuppressWarnings("unchecked")
  Stream<StartsMessage> filterStream = (Stream<StartsMessage>) Mockito.mock(Stream.class);
  @SuppressWarnings("unchecked")
  Stream<StartsAggMessage> windowStream = (Stream<StartsAggMessage>) Mockito.mock(Stream.class);

  // note: don't call start() on the operators and start up a new thread while testing
  StartsJsonDeserOperator deserOperator = new StartsJsonDeserOperator(inputStream, deserStream);
  StartsFilterOperator filterOperator = new StartsFilterOperator(deserStream, filterStream);

}

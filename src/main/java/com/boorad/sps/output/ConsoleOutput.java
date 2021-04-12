package com.boorad.sps.output;


import com.boorad.sps.message.StartsAggMessage;
import com.boorad.sps.stream.Stream;
import com.google.gson.Gson;

public class ConsoleOutput extends Output<StartsAggMessage> {

  Gson gson = new Gson();

  public ConsoleOutput(Stream<StartsAggMessage> inStream) {
    super(inStream);
  }

  @Override
  public void output(StartsAggMessage msg) {
    System.out.println(gson.toJson(msg));
  }

}

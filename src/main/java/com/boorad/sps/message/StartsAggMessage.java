package com.boorad.sps.message;

public class StartsAggMessage {

  public StartsAggMessage() {}

  public String device;
  public String title;
  public String country;
  public long sps;

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    sb.append(device).append(',');
    sb.append(title).append(',');
    sb.append(country).append(',');
    sb.append(sps);
    sb.append(']');
    return sb.toString();
  }

}

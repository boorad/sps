package com.boorad.sps.message;

public class StartsMessage {

  public StartsMessage() {}

  public String device;
  public String sev;
  public String title;
  public String country;
  public long time;

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    sb.append(device).append(',');
    sb.append(sev).append(',');
    sb.append(title).append(',');
    sb.append(country).append(',');
    sb.append(time);
    sb.append(']');
    return sb.toString();
  }

}

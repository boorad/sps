package com.boorad.sps.input;

public class StartsInput<T> implements Input<T> {

  private String url;

  public StartsInput(String url) {
    this.url = url;
  }

  public void open() {

  }

  public T nextRecord() {
    String ret = "";
    return (T)ret;
  }

  public void close() {

  }

}

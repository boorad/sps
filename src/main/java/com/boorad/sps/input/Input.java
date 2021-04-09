package com.boorad.sps.input;

public interface Input<T> {

  public void open();

  public T nextRecord();

  public void close();

}

package com.boorad.sps.input;

public interface Input<T> {

  public boolean open();

  public T nextRecord();

  public void close();

}

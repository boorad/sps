package com.boorad.sps.output;

import com.boorad.sps.stream.Stream;

import java.util.NoSuchElementException;

public abstract class Output<T> implements Runnable {

  private Stream<T> inStream;

  public Output(Stream<T> inStream) {
    this.inStream = inStream;
  }

  public void start() {
    Thread thread = new Thread(this);
    thread.start();
  }

  @Override
  public void run() {
    while(true) {
      try {
        T msg = inStream.remove();
        if( msg != null ) {
          //LOG.info(msg.toString());
          output(msg);
        }
      } catch( NoSuchElementException nsee ) {}
    }
  }

  public abstract void output(T msg);

}

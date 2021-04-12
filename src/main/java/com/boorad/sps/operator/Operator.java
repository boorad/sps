package com.boorad.sps.operator;

import com.boorad.sps.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

public abstract class Operator<I, O> implements Runnable {

  private Logger LOG = LoggerFactory.getLogger(Operator.class);

  private Stream<I> inStream;
  private Stream<O> outStream;

  public Operator(Stream<I> inStream, Stream<O> outStream) {
    this.inStream = inStream;
    this.outStream = outStream;
  }

  public void start() {
    Thread thread = new Thread(this);
    thread.start();
  }

  @Override
  public void run() {
    while(true) {
      try {
        I msg = inStream.remove();
        if( msg != null ) {
          //LOG.info(msg.toString());
          process(msg, outStream);
        }
      } catch( NoSuchElementException nsee ) {}
    }
  }

  public abstract void process(I msg, Stream<O> out);

}

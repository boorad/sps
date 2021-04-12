package com.boorad.sps.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

public class Stream<T> {

  private Logger LOG = LoggerFactory.getLogger(Stream.class);

  private LinkedBlockingQueue<T> queue;

  public Stream() {
    queue = new LinkedBlockingQueue<T>();
  }

  public void add(T msg) {
    if (msg != null) {
      queue.offer(msg);
    }
  }

  public T remove() {
    return queue.poll();
  }

}

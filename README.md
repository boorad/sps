# SPS
Stream Starts Per Second - Stream Processing Application

## Quick Start

#### Dependencies

 * Java 8 or higher
 * Maven

#### Build

    mvn clean package

#### Code coverage report

    mvn test jacoco:report

Then see `target/site/jacoco/index.html`

#### Execute

    java -jar target/sps-*.jar

#### Stop Processing

Press `Ctrl-C`

## Design

### initial plan

First, get fatjar building and executing.  Then get keyboard interrupt working.

Will be good to get a single-threaded in-memory, hardwired end-to-end version,
and then add in `Stream`, `Input`, `Operator`, `Output` objects.  Finally document
a plan for multi-threading, multi-machine, and any other future work.

### Classes/Interfaces
 * `Stream` - object for enqueueing and dequeueing messages between `Operator`s
 * `Input` - object for a data source (maybe rename to Source)
 * `Operator` - object for doing a unit of work in `process()` method, and for 
   wiring our application's DAG together (will have `Stream` in and `Stream` out)
 * `Output` - object for a data sink (maybe rename to Sink)

## Future Work

After running and testing this application for longer runtimes, we will need more
error handling and re-connecting to the `Input` source after failures.

This is lightly-threaded (each `Operator` has its own thread, but just one).
We should have worker threads to spread out message processing
on a single machine, and then implement serializable messages and operators to
pass work on to larger clusters of machines.  That would also require using something
Netty-shaped for nodes to pass work tasks (serializable) and data (bytes) around, 
as well as a resource and application manager like Yarn, Mesos, or K8S.

Further, this application is also in-memory only.  Persisting state to disk in 
`Stream` objects would make it more resilient to this node failing,
or if multi-machine any node's failures.

We will need more flexible options than `process()` in `Operator`s.  For instance
methods that either broadcast/fan out, or reduce/flatmap to a smaller stream or
fewer operators.  This is assuming business requirements change and more forms of
these data are required downstream.  `StartsWindowOperator` could have been broken
into two `Operator`s, one doing the windowing, one doing the reducing.

The current implementation only has `ConsoleOutput`, but we could easily implement
a `FileOutput` sink using `BufferedOutputStream` / `FileOutputStream`.

For QA/correctness work, we should instrument the count of messages in and out of the `Stream`
instances, to make sure we are not dropping any on the floor.  We could also instrument
the size of the queue in each `Stream`.  This instrumentation
data could also be used for auto-scaling features to handle variable demand within
a day, as well as features for `Operator`s and `Stream`s in the application to 
communicate backpressure to each other.

Also, for memory usage (which actually seems stable), we should review `StartsWindowOperator`
as there may be some inner-loop allocations that could reuse objects.  For CPU usage,
we may want to investigate small timers inside tight `while( true )` loops, if we
don't drop messages or back up queues.

There seems to be additional data anomaly handling that is required.  `busted data`
is sometimes seen in the data, even though `sev` == `success`.  Also, we could
consolidate some titles with a lookup table.  For example, `oitnb` could be aggregated
with `orange is the new black` values for a more accurate count.

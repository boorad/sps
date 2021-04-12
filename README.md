# SPS
Stream Starts Per Second - Stream Processing Application

## Quick Start

#### Dependencies

 * Java 8 or higher
 * Maven

#### Build

    mvn clean package

#### Execute

    java -jar target/sps-*.jar

#### Stop Processing

Press `Ctrl-C`

## Design

### initial plan

First, get fatjar building and executing.  Then get keyboard interrupt working.

Will be good to get a single-threaded in-memory, hardwired end-to-end version,
and then add in Stream, Input, Operator, Output objects.  Finally lay
out (but don't implement) plan for multi-threading, multi-machine, etc.

### Classes/Interfaces
 * **Stream** - object for declaring our simple DAG in code, and connecting Operators together
 * **Input** - object for a data source (maybe rename to Source)
 * **Operator** - object for doing a unit of work (will have Input and Output)
 * **Output** - object for a data sink (maybe rename to Sink)

## Future Work

This is lightly-threaded (each operator has its own thread, but just one).
We should have worker threads to spread out message processing
on a single machine, and then implement serializable messages and operators to
pass work on to larger clusters of machines.  That would also require using something
Netty-shaped for nodes to pass work tasks and data around, as well as a resource
and application manager like Yarn, Mesos, K8S.

Further, this application is also in-memory only.  Persisting state to disk in
either Stream or Operator objects would make it more resilient to this node failing,
or if multi-machine any node's failures.

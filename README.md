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
and then add in Stream, Input, Operator, Output, Message objects.  Finally lay
out (but don't implement) plan for multi-threading, multi-machine, etc.

### Classes
 * **Stream** - object for declaring our simple DAG in code, and connecting Operators together
before calling `execute()`.
 * **Input** - object for a data source (maybe rename to Source)
 * **Operator** - object for doing a unit of work (will have Input and Output)
 * **Output** - object for a data sink (maybe rename to Sink)
 * **Message** - object representing a stream event/message.  In this application, a Stream Start

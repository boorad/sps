package com.boorad.sps;

import com.boorad.sps.input.StartsInput;
import com.boorad.sps.message.StartsAggMessage;
import com.boorad.sps.message.StartsMessage;
import com.boorad.sps.operator.StartsFilterOperator;
import com.boorad.sps.operator.StartsJsonDeserOperator;
import com.boorad.sps.operator.StartsWindowOperator;
import com.boorad.sps.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  (Event) Stream Processing Application for (Title) Stream Starts
 *
 */
public class SPSApp {

    static Logger LOG = LoggerFactory.getLogger(SPSApp.class);

    public static void main( String[] args ) {

        // TODO: args or config
        final String url = "https://tweet-service.herokuapp.com/sps";
        final long windowSize = 1 * 1000L; // 1 sec

        // stream input
        final StartsInput<String> input = new StartsInput<>(url);
        Stream<String> inputStream = new Stream<>();

        // string => json => pojo operator
        Stream<StartsMessage> deserStream = new Stream<>();
        final StartsJsonDeserOperator jsonDeserOperator = new StartsJsonDeserOperator(inputStream, deserStream);
        jsonDeserOperator.start();

        // filter operator
        Stream<StartsMessage> filterStream = new Stream<>();
        final StartsFilterOperator filterOperator = new StartsFilterOperator(deserStream, filterStream);
        filterOperator.start();

        // 1 sec window operator
        Stream<StartsAggMessage> windowStream = new Stream<>();
        final StartsWindowOperator windowOperator = new StartsWindowOperator(filterStream, windowStream, windowSize);
        windowOperator.start();




        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOG.info("Processing interrupted");
                input.close();
            }
        });

        // open the input
        input.open();

        while (true) {
            inputStream.add(input.nextRecord());
        }

/*
            // reset for this iteration
            pojo = null;
            success = null;

            String record = input.nextRecord();

            // TODO: these null checks are janky, use Collector or handle in Stream?
            if( record != null ) {
                pojo = jsonDeserOperator.process(record);
            }

            if( pojo != null ) {
                success = filterOperator.process(pojo);
            }

            if( success != null ) {
                windowOperator.process(success);
            }
        }
*/
    }

}

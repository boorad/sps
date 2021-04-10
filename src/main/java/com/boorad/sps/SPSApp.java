package com.boorad.sps;

import com.boorad.sps.input.StartsInput;
import com.boorad.sps.message.StartsMessage;
import com.boorad.sps.operator.StartsFilterOperator;
import com.boorad.sps.operator.StartsJsonDeserOperator;
import com.boorad.sps.operator.StartsWindowOperator;
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
        final long lateDataWindowSize = 1 * 60 * 1000; // 1 min

        // stream input
        final StartsInput<String> input = new StartsInput<>(url);

        // string => json => pojo operator
        final StartsJsonDeserOperator jsonDeserOperator = new StartsJsonDeserOperator();
        StartsMessage pojo = new StartsMessage();

        // filter operator
        final StartsFilterOperator filterOperator = new StartsFilterOperator();
        StartsMessage success = new StartsMessage();

        // 1 sec window operator
        final StartsWindowOperator windowOperator = new StartsWindowOperator(windowSize, lateDataWindowSize);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOG.info("Processing interrupted");
                input.close();
            }
        });

        // open the input stream
        input.open();

        while (true) {
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

    }

}

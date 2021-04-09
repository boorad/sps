package com.boorad.sps;

import com.boorad.sps.input.StartsInput;
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

        final StartsInput<String> inputFormat = new StartsInput<>(url);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOG.info("Processing interrupted");
                inputFormat.close();
            }
        });

        inputFormat.open();
        LOG.info("Starting stream processing");
        while (true) {
            String record = inputFormat.nextRecord();
        }

    }

}

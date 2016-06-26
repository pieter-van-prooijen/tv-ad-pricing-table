package com.example.tvadpricing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point of the application
 */

@SpringBootApplication
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    /*
     * Set spring properties using the --prop=value syntax.
     *
     */
    public static void main(String... args) {
        SpringApplication.run(Main.class, args);
    }
}

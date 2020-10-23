package fhnw.dreamteam.stockstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StocksTrackerApplication {
    /**
     * The main application.
     *
     * @param args Arguments passed in the console.
     */
    public static void main(final String[] args) {
        SpringApplication.run(StocksTrackerApplication.class, args);
    }
}

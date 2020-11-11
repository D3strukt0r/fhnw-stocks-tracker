package fhnw.dreamteam.stockstracker;

import fhnw.dreamteam.stockstracker.data.seeddata.Seeder;
import fhnw.dreamteam.stockstracker.data.seeddata.UserSeed;
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

        // run seed data
        Seeder.seedData();
    }
}

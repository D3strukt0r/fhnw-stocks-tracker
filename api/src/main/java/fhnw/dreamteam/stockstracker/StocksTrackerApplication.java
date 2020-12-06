package fhnw.dreamteam.stockstracker;

import fhnw.dreamteam.stockstracker.data.seeddata.Seeder;
import fhnw.dreamteam.stockstracker.data.seeddata.UserSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // allow for development from the local client
                registry
                    .addMapping("/**")
                    .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                    .allowedOrigins("http://localhost:8082", "http://localhost:63342", "http://localhost:8080", "http://127.0.0.1:8082", "http://localhost:63343")
                    .allowCredentials(true);
            }
        };
    }
}

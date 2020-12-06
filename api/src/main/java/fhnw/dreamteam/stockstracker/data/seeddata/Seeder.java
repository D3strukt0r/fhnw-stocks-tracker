package fhnw.dreamteam.stockstracker.data.seeddata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Seeder {
    @Autowired
    private UserSeed userSeed;

    @Autowired
    private CurrencySeeder currencySeeder;

    @Autowired
    private StocksSeeder stocksSeeder;

    @PostConstruct
    public void init() {
        seedData();
    }

    public void seedData() {
        try {
            // add seed code here
            userSeed.seedUsers();
            currencySeeder.seedCurrency();
            stocksSeeder.seedStocks();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

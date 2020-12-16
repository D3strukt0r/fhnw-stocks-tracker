package fhnw.dreamteam.stockstracker.data.seeddata;

import fhnw.dreamteam.stockstracker.data.models.Stock;
import fhnw.dreamteam.stockstracker.data.repository.CurrencyRepository;
import fhnw.dreamteam.stockstracker.data.repository.UserRepository;
import fhnw.dreamteam.stockstracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StocksSeeder {

    @Autowired
    private StockService stockService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    public void seedStocks() throws Exception {
        Stock stock1 = new Stock("stock1", 23.54, 20, currencyRepository.findByName("CHF"), userRepository.findByUsername("testuser1"), new Date(), true, 1.3);
        Stock stock2 = new Stock("stock2", 15.0, 10, currencyRepository.findByName("CHF"), userRepository.findByUsername("testuser1"), new Date(), true, 1.3);
        Stock stock3 = new Stock("stock3", 56.43, 40, currencyRepository.findByName("CHF"), userRepository.findByUsername("testuser2"), new Date(), true, 1.3);
        Stock stock4 = new Stock("stock4", 104.54, 50, currencyRepository.findByName("CHF"), userRepository.findByUsername("testuser3"), new Date(), true, 1.3);
        stockService.createStock(stock1);
        stockService.createStock(stock2);
        stockService.createStock(stock3);
        stockService.createStock(stock4);
    }
}

package fhnw.dreamteam.stockstracker.data.seeddata;

import fhnw.dreamteam.stockstracker.data.models.Stock;
import fhnw.dreamteam.stockstracker.data.repository.CurrencyRepository;
import fhnw.dreamteam.stockstracker.data.repository.UserRepository;
import fhnw.dreamteam.stockstracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StocksSeeder {

    @Autowired
    private StockService stockService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    public void seedStocks() throws Exception {
        Stock stock1 = new Stock("stock1", 1, 1, currencyRepository.findByName("CHF"), userRepository.findByUsername("testuser1"));
        Stock stock2 = new Stock("stock2", 2, 2, currencyRepository.findByName("CHF"), userRepository.findByUsername("testuser2"));
        Stock stock3 = new Stock("stock3", 3, 3, currencyRepository.findByName("CHF"), userRepository.findByUsername("testuser3"));
        stockService.createStock(stock1);
        stockService.createStock(stock2);
        stockService.createStock(stock3);
    }
}

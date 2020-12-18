package fhnw.dreamteam.stockstracker.data.seeddata;

import fhnw.dreamteam.stockstracker.data.models.Currency;
import fhnw.dreamteam.stockstracker.data.repository.UserRepository;
import fhnw.dreamteam.stockstracker.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencySeeder {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private UserRepository userRepository;

    public void seedCurrency() throws Exception {
        Currency currency1 = new Currency("CHF", true, userRepository.findByUsername("testuser1"));
        Currency currency2 = new Currency("USD", true, userRepository.findByUsername("testuser1"));
        Currency currency3 = new Currency("CHF", true, userRepository.findByUsername("testuser2"));
        Currency currency4 = new Currency("USD", true, userRepository.findByUsername("testuser2"));
        Currency currency5 = new Currency("CHF", true, userRepository.findByUsername("testuser3"));
        Currency currency6 = new Currency("USD", true, userRepository.findByUsername("testuser3"));
        currencyService.createCurrency(currency1);
        currencyService.createCurrency(currency2);
        currencyService.createCurrency(currency3);
        currencyService.createCurrency(currency4);
        currencyService.createCurrency(currency5);
        currencyService.createCurrency(currency6);
    }
}

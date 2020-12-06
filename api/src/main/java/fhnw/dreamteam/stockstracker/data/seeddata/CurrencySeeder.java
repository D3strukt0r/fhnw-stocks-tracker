package fhnw.dreamteam.stockstracker.data.seeddata;

import fhnw.dreamteam.stockstracker.data.models.Currency;
import fhnw.dreamteam.stockstracker.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencySeeder {

    @Autowired
    private CurrencyService currencyService;

    public void seedCurrency() throws Exception {
        Currency currency1 = new Currency("CHF");
        currencyService.createCurrency(currency1);
    }
}

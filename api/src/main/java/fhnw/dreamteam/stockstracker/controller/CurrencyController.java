package fhnw.dreamteam.stockstracker.controller;

import fhnw.dreamteam.stockstracker.data.models.Currency;
import fhnw.dreamteam.stockstracker.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @GetMapping(path = "/currency", produces = "application/json")
    public List<Currency> getCurrencies() {
        return currencyService.getAll();
    }
}

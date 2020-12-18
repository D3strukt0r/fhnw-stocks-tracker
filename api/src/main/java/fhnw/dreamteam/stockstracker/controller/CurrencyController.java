package fhnw.dreamteam.stockstracker.controller;

import fhnw.dreamteam.stockstracker.data.models.Currency;
import fhnw.dreamteam.stockstracker.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @GetMapping(path = "/currency", produces = "application/json")
    public List<Currency> getCurrencies() {
        return currencyService.getAllByUser();
    }

    @PostMapping(path = "/currency", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> postCurrency(@RequestBody Currency currency) {
        Currency newCurrency;
        try {
            newCurrency = currencyService.createCurrency(currency);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_ACCEPTABLE,
                e.getConstraintViolations().iterator().next().getMessage()
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}

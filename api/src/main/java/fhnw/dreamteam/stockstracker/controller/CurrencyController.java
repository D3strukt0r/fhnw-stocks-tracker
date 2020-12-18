package fhnw.dreamteam.stockstracker.controller;

import fhnw.dreamteam.stockstracker.data.models.Currency;
import fhnw.dreamteam.stockstracker.data.models.Stock;
import fhnw.dreamteam.stockstracker.data.repository.CurrencyRepository;
import fhnw.dreamteam.stockstracker.data.repository.StockRepository;
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
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyRepository currencyRepository;

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

    @GetMapping(path = "/currency", produces = "application/json")
    public List<Currency> getCurrencies() {
        return currencyService.getAllByUser();
    }


    @GetMapping(path = "/currency/{currencyId}", produces = "application/json")
    public ResponseEntity<Currency> getCurrency(@PathVariable(value = "currencyId") final String currencyId) {
        Optional<Currency> currency = null;
        try {
            currency = currencyRepository.findById(Long.parseLong(currencyId));
            if (!currency.isPresent()) {
                throw new Exception("Currency not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(currency.get());
    }

    @PutMapping(path = "/currency/{currencyId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Currency> putStock(
        @RequestBody final Currency currency,
        @PathVariable(value = "currencyId") final String currencyId
    ) {
        Currency newCurrency = null;
        try {
            currency.setId(Long.parseLong(currencyId));
            newCurrency = currencyService.editCurrency(currency);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().body(newCurrency);
    }

}

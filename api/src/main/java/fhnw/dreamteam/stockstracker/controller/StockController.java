package fhnw.dreamteam.stockstracker.controller;


import fhnw.dreamteam.stockstracker.data.models.Stock;
import fhnw.dreamteam.stockstracker.service.StockService;
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
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping(path = "/stock", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> postCustomer(@RequestBody Stock stock) {
        try {
            stock = stockService.createStock(stock);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{stockId}")
            .buildAndExpand(stock.getId()).toUri();

        return ResponseEntity.created(location).body(stock);
    }

    @GetMapping(path = "/stock", produces = "application/json")
    public List<Stock> getStocks() {
        return stockService.findAllStocks();
    }

    /*@GetMapping(path = "/customer/{customerId}", produces = "application/json")
    public ResponseEntity<Stock> getCustomer(@PathVariable(value = "customerId") final String customerId) {
        Stock customer = null;
        try {
            customer = stockService.findCustomerById(Long.parseLong(customerId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(customer);
    }

    @PutMapping(path = "/customer/{customerId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> putCustomer(@RequestBody final Stock customer, @PathVariable(value = "customerId") final String customerId) {
        try {
            customer.setId(Long.parseLong(customerId));
            customer = stockService.editCustomer(customer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().body(customer);
    }

    @DeleteMapping(path = "/customer/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(value = "customerId") final String customerId) {
        try {
            stockService.deleteCustomer(Long.parseLong(customerId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().build();
    }*/
}

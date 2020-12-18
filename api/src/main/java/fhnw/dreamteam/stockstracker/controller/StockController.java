package fhnw.dreamteam.stockstracker.controller;

import fhnw.dreamteam.stockstracker.data.models.Stock;
import fhnw.dreamteam.stockstracker.data.repository.StockRepository;
import fhnw.dreamteam.stockstracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class StockController {
    /**
     * The stock service.
     */
    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @PostMapping(path = "/stock", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> postStock(@RequestBody final Stock stock) {
        Stock newStock;
        try {
            newStock = stockService.createStock(stock);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_ACCEPTABLE,
                e.getConstraintViolations().iterator().next().getMessage()
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{stockId}")
            .buildAndExpand(newStock.getId())
            .toUri();

        return ResponseEntity.created(location).body(newStock);
    }

    @GetMapping(path = "/stock", produces = "application/json")
    public List<Stock> getStocks() {
        return stockService.getAllByUser();
    }

    /**
     * The response handler to get a customer.
     *
     * @param stockId The customer.
     *
     * @return Returns the customer's information.
     */
    @GetMapping(path = "/stock/{stockId}", produces = "application/json")
    public ResponseEntity<Stock> getStock(@PathVariable(value = "stockId") final String stockId) {
        Optional<Stock> stock = null;
        try {
            stock = stockRepository.findById(Long.parseLong(stockId));
            if (!stock.isPresent()) {
                throw new Exception("User not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(stock.get());
    }

     /**
      * The response handler to edit a customer's existing information.
      *
      * @param stock   The new customer information.
      * @param stockId The customer's ID.
      *
      * @return The new customer information.
      */
     @PutMapping(path = "/stock/{stockId}", consumes = "application/json", produces = "application/json")
     public ResponseEntity<Stock> putStock(
         @RequestBody final Stock stock,
         @PathVariable(value = "stockId") final String stockId
     ) {
         Stock newStock = null;
         try {
             stock.setId(Long.parseLong(stockId));
             newStock = stockService.editStock(stock);
         } catch (Exception e) {
             throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
         }
         return ResponseEntity.accepted().body(newStock);
     }

     /**
      * The response handler to delete a stock.
      *
      * @param stockId The stock's ID.
      *
      * @return Returns a successful response.
      */
     @DeleteMapping(path = "/stock/{stockId}", consumes = "application/json", produces = "application/json")
     public ResponseEntity<Void> deleteStock(
         @PathVariable (value = "stockId") final String stockId
     ) {
         try {
             stockService.deleteStock(Long.parseLong(stockId));
         } catch (Exception e) {
             throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
         }
         return ResponseEntity.accepted().build();
     }
}

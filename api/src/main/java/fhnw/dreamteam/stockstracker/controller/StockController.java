package fhnw.dreamteam.stockstracker.controller;

import fhnw.dreamteam.stockstracker.data.models.Stock;
import fhnw.dreamteam.stockstracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class StockController {
    /**
     * The stock service.
     */
    @Autowired
    private StockService stockService;

    /**
     * The response handler for adding a stock.
     *
     * @param stock The stock to add.
     *
     * @return Returns the created stock.
     */
    @PostMapping(path = "/stock", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> postCustomer(@RequestBody final Stock stock) {
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

    /**
     * The response handler to get all stocks.
     *
     * @return Returns all stocks.
     */
    @GetMapping(path = "/stock", produces = "application/json")
    public List<Stock> getStocks() {
        return stockService.findAllStocks();
    }

    // /**
    // * The response handler to get a customer.
    // *
    // * @param customerId The customer.
    // *
    // * @return Returns the customer's information.
    // */
    // @GetMapping(path = "/customer/{customerId}", produces = "application/json")
    // public ResponseEntity<Stock> getCustomer(@PathVariable(value = "customerId")
    // final String customerId) {
    // Stock customer = null;
    // try {
    // customer = stockService.findCustomerById(Long.parseLong(customerId));
    // } catch (Exception e) {
    // throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    // }
    // return ResponseEntity.ok(customer);
    // }

    // /**
    //  * The response handler to edit a customer's existing information.
    //  *
    //  * @param customer   The new customer information.
    //  * @param customerId The customer's ID.
    //  *
    //  * @return The new customer information.
    //  */
    // @PutMapping(path = "/customer/{customerId}", consumes = "application/json", produces = "application/json")
    // public ResponseEntity<Stock> putCustomer(@RequestBody final Stock customer,
    //         @PathVariable(value = "customerId") final String customerId) {
    //     try {
    //         customer.setId(Long.parseLong(customerId));
    //         customer = stockService.editCustomer(customer);
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
    //     }
    //     return ResponseEntity.accepted().body(customer);
    // }

    // /**
    //  * The response handler to delete a customer.
    //  *
    //  * @param customerId The customer's ID.
    //  *
    //  * @return Returns a successful response.
    //  */
    // @DeleteMapping(path = "/customer/{customerId}")
    // public ResponseEntity<Void> deleteCustomer(@PathVariable(value = "customerId") final String customerId) {
    //     try {
    //         stockService.deleteCustomer(Long.parseLong(customerId));
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
    //     }
    //     return ResponseEntity.accepted().build();
    // }
}

package fhnw.dreamteam.stockstracker.service;

import fhnw.dreamteam.stockstracker.data.models.Stock;
import fhnw.dreamteam.stockstracker.data.repository.StockRepository;
import fhnw.dreamteam.stockstracker.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class StockService {
    /**
     * The stock repository.
     */
    @Autowired
    private StockRepository stockRepository;

    /**
     * The user service.
     */
    @Autowired
    private UserService userService;

    /**
     * Create a new {@link Stock}.
     *
     * @param stock The stock to be added
     *
     * @return Returns the newly added stock.
     *
     * @throws Exception
     */
    public Stock createStock(@Valid final Stock stock) throws Exception {
        if (stock.getUser() == null) {
            stock.setUser(userService.getCurrentUser());
        }
        return stockRepository.save(stock);
    }

    /**
     * Edit an existing {@link Stock}.
     * @param stock The stock to be edited, with the new information.
     *
     * @return Returns the newly added stock.
     *
     * @throws Exception
     */
    public Stock editStock(@Valid final Stock stock) throws Exception {
        Optional<Stock> dbStock = stockRepository.findById(stock.getId());
        if (stock.getId() != null && dbStock != null && dbStock.isPresent()) {
            dbStock.get().setName(stock.getName());
            dbStock.get().setPrice(stock.getPrice());
            dbStock.get().setQuantity(stock.getQuantity());
            return stockRepository.save(dbStock.get());
        } else {
            throw new Exception("Stock could not be found.");
        }
    }

    /**
     * Deletes a {@link Stock}.
     *
     * @param stockId The stock's ID to delete.
     */
    public void deleteStock(final Long stockId) {
        stockRepository.deleteById(stockId);
    }

    // /**
    //  * Find a stock by it's ID.
    //  *
    //  * @param stockId The {@link Stock}'s ID.
    //  *
    //  * @return Returns the stock.
    //  *
    //  * @throws Exception Throws if nothing found.
    //  */
    // public Stock findStockById(final Long stockId) throws Exception {
    //     List<Stock> stockList = stockRepository.findById(stockId);
    //     if (stockList.isEmpty()) {
    //         throw new Exception("No customer with ID " + stockId + " found.");
    //     }
    //     return stockList.get(0);
    // }

    /**
     * Get all stocks.
     *
     * @return Returns all stocks.
     */
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    /**
     * Get all stocks by current user.
     *
     * @return Returns all stocks of a current user.
     */
    public List<Stock> getAllByUser() {
        return stockRepository.findAllByUser(userService.getCurrentUser().getId());
    }
}

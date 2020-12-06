package fhnw.dreamteam.stockstracker.service;

import fhnw.dreamteam.stockstracker.data.models.Currency;
import fhnw.dreamteam.stockstracker.data.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CurrencyService {
    /**
     * The currency repository.
     */
    @Autowired
    private CurrencyRepository currencyRepository;

    /**
     * Create a new {@link fhnw.dreamteam.stockstracker.data.models.Currency}.
     *
     * @param currency The currency to be added
     *
     * @return Returns the newly added currency.
     *
     * @throws Exception
     */
    public Currency createCurrency(@Valid final Currency currency) throws Exception {
        return currencyRepository.save(currency);
    }

    /**
     * Edit an existing {@link Currency}.
     * @param currency The currency to be edited, with the new information.
     *
     * @return Returns the newly added currency.
     *
     * @throws Exception
     */
    public Currency editCurrency(@Valid final Currency currency) throws Exception {
        Optional<Currency> dbCurrency = currencyRepository.findById(currency.getId());
        if (currency.getId() != null && dbCurrency != null && dbCurrency.isPresent()) {
            dbCurrency.get().setName(currency.getName());
            return currencyRepository.save(dbCurrency.get());
        } else {
            throw new Exception("Currency could not be found.");
        }
    }

    /**
     * Deletes a {@link Currency}.
     *
     * @param currencyID The stock's ID to delete.
     */
    public void deleteCurrency(final Long currencyID) {
        currencyRepository.deleteById(currencyID);
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
     * Get all currencies.
     *
     * @return Returns all currencies.
     */
    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }
}

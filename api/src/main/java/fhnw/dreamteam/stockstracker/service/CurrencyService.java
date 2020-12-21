package fhnw.dreamteam.stockstracker.service;

import fhnw.dreamteam.stockstracker.data.models.Currency;
import fhnw.dreamteam.stockstracker.data.models.Stock;
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
     * The user service.
     */
    @Autowired
    private UserService userService;

    public Currency createCurrency(@Valid final Currency currency) throws Exception {
        if (currency.getUser() == null) {
            currency.setUser(userService.getCurrentUser());
        }

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

        if(dbCurrency.isPresent() && userService.getCurrentUser().getUsername() != dbCurrency.get().getUser().getUsername()) {
            throw new Exception("You are not permitted to edit this currency");
        }

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
    public void deleteCurrency(final Long currencyID) throws Exception {
        Optional<Currency> dbCurrency = currencyRepository.findById(currencyID);

        if(dbCurrency.isPresent() && userService.getCurrentUser().getUsername() != dbCurrency.get().getUser().getUsername()) {
            throw new Exception("You are not permitted to delete this currency");
        }

        currencyRepository.deleteById(currencyID);
    }

    /**
     * Get all currencies.
     *
     * @return Returns all currencies.
     */
    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }

    /**
     * Get all currencies by current user.
     *
     * @return Returns all stocks of a current user.
     */
    public List<Currency> getAllByUser() {
        return currencyRepository.findAllByUser(userService.getCurrentUser().getId());
    }
}

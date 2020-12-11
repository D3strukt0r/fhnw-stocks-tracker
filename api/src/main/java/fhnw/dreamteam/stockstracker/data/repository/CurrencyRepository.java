package fhnw.dreamteam.stockstracker.data.repository;

import fhnw.dreamteam.stockstracker.data.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    /**
     * Find a currency by it's ID.
     *
     * @param id The ID of the stock.
     *
     * @return Returns the stock.
     */
    Currency findById(int id);

    /**
     * Find a currency by it's name.
     *
     * @param name The ID of the stock.
     *
     * @return Returns the stock.
     */
    Currency findByName(String name);
}

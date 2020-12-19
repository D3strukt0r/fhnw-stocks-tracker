package fhnw.dreamteam.stockstracker.data.repository;

import fhnw.dreamteam.stockstracker.data.models.Currency;
import fhnw.dreamteam.stockstracker.data.models.Stock;
import fhnw.dreamteam.stockstracker.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * Find currencies by it's current user.
     *
     * @param userID The ID of the user of the stock.
     *
     * @return Returns the stock.
     */
    @Query("SELECT c FROM Currency c WHERE c.user.id = :userID")
    List<Currency> findAllByUser(@Param("userID") long userID);

    /**
     * Find currency by name for it's current user.
     *
     * @param user The user of the stock.
     * @param name The name of the currency
     *
     * @return Returns the stock.
     */
    Currency findByUserAndName(User user, String name);
}

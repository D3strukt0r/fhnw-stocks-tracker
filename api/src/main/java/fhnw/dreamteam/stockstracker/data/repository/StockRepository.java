package fhnw.dreamteam.stockstracker.data.repository;

import fhnw.dreamteam.stockstracker.data.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    /**
     * Find a stock by it's ID.
     *
     * @param id The ID of the stock.
     *
     * @return Returns the stock.
     */
    Stock findById(int id);

    /**
     * Find a stock by it's name.
     *
     * @param name The name of the stock.
     *
     * @return Returns the stock.
     */
    List<Stock> findByName(String name);

    /**
     * Find a stock by it's current user.
     *
     * @param userID The ID of the user of the stock.
     *
     * @return Returns the stock.
     */
    @Query("SELECT s FROM Stock s WHERE s.user.id = :userID")
    List<Stock> findAllByUser(@Param("userID") long userID);
}

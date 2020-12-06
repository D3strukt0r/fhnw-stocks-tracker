package fhnw.dreamteam.stockstracker.data.repository;

import fhnw.dreamteam.stockstracker.data.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
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
     * @param name The ID of the stock.
     *
     * @return Returns the stock.
     */
    // Stock findByName(String name);
}

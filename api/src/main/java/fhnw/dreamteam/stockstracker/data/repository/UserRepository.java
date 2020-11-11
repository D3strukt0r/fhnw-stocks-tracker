package fhnw.dreamteam.stockstracker.data.repository;

import fhnw.dreamteam.stockstracker.data.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Stock, Long> {
    /**
     * Get all stocks by ...
     *
     * @return Returns all stocks by ...
     */
    List<User> getAllBy();

    /**
     * Find a stock by it's ID.
     *
     * @param id The ID of the stock.
     *
     * @return Returns the stock.
     */
    Stock findById(int id);
}

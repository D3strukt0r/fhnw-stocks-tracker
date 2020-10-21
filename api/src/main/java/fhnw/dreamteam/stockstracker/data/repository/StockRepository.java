package fhnw.dreamteam.stockstracker.data.repository;

import fhnw.dreamteam.stockstracker.data.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> getAllBy();
    Stock findById(int id);
}

package fhnw.dreamteam.stockstracker.service;

import fhnw.dreamteam.stockstracker.data.models.Stock;
import fhnw.dreamteam.stockstracker.data.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public Stock createStock(@Valid Stock stock) throws Exception {
        return stockRepository.save(stock);
    }

    public Stock editStock(@Valid Stock stock) throws Exception {
        Optional<Stock> dbStock = stockRepository.findById(stock.getId());
        if(stock.getId() != null && dbStock != null && dbStock.isPresent()) {
            dbStock.get().setName(stock.getName());
            return stockRepository.save(dbStock.get());
        } else {
            throw new Exception("Stock could not be found.");
        }
    }

    public void deleteCustomer(Long customerId)
    {
        stockRepository.deleteById(customerId);
    }

    /*public Stock findStockById(Long stockId) throws Exception {
        List<Stock> stockList = stockRepository.findById(stockId);
        if(stockList.isEmpty()){
            throw new Exception("No customer with ID "+stockId+" found.");
        }
        return stockList.get(0);
    }*/

    public List<Stock> findAllStocks() {
        return stockRepository.getAllBy();
    }
}

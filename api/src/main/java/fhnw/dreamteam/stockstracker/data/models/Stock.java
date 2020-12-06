package fhnw.dreamteam.stockstracker.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Stock {

    public Stock(){
    }

    /**
     * The ID of the stock.
     */
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    /**
     * The name of the stock.
     */
    @NotEmpty(message = "Please provide a name.")
    @Getter
    @Setter
    private String name;

    /**
     * The price that the stock was bought for.
     */
    @Getter
    @Setter
    private Double price;

    /**
     * The quantity of the bought stock.
     */
    @Getter
    @Setter
    private Integer quantity;

    /**
     * The currency used for the stock.
     */
    @ManyToOne
    @Getter
    @Setter
    private Currency currency;

    @ManyToOne
    @JsonIgnore
    @Setter
    @Getter
    private User user;

    public Stock(
        @NotEmpty(message = "Please enter a name.") String name,
        @NotEmpty(message = "Please provide the price.") Double price,
        @NotEmpty(message = "Please provide the quantity.") Integer quantity,
        @NotEmpty(message = "Please provide the currency.") Currency currency,
        @NotEmpty(message = "Please provide the user.") User user
    ) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.currency = currency;
        this.user = user;
    }
}

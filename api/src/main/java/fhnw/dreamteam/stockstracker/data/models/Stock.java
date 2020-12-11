package fhnw.dreamteam.stockstracker.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    @Getter
    @Setter
    @NotEmpty(message = "Please provide a name.")
    private String name;

    /**
     * The price that the stock was bought for.
     */
    @Getter
    @Setter
    @NotNull(message = "Please provide a price.")
    private Double price;

    /**
     * The quantity of the bought stock.
     */
    @Getter
    @Setter
    @NotNull(message = "Please provide a quantity.")
    private Integer quantity;

    /**
     * The currency used for the stock.
     */
    @ManyToOne
    @Getter
    @Setter
    @NotNull(message = "Please provide a currency.")
    private Currency currency;

    @Getter
    @Setter
    @NotNull(message = "Please provide a purchase date.")
    private Date purchaseDate;

    @Getter
    @Setter
    @NotNull(message = "Please provide say if it is active or not.")
    private Boolean isActive;

    @Getter
    @Setter
    @NotNull(message = "Please provide a conversion rate.")
    private Double conversionRate;

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
        @NotEmpty(message = "Please provide the user.") User user,
        @NotEmpty(message = "Please provide a purchase date.") Date purchaseDate,
        @NotEmpty(message = "Please provide say if it is active or not.") Boolean isActive,
        @NotEmpty(message = "Please provide a conversion rate.") Double conversionRate
    ) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.currency = currency;
        this.user = user;
        this.purchaseDate = purchaseDate;
        this.isActive = isActive;
        this.conversionRate = conversionRate;
    }
}

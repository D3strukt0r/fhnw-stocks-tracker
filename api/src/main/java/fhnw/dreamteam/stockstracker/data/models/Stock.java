package fhnw.dreamteam.stockstracker.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
// import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Stock {
    /**
     * The ID of the stock.
     */
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    /**
     * The name of the stock.
     */
    @NotEmpty(message = "Please provide a name.")
    @Getter
    @Setter
    private String name;

    /**
     * The currency used for the stock.
     */
    @ManyToOne
    @JsonIgnore
    @Getter
    @Setter
    private Currency currency;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Stock() {
    }
}

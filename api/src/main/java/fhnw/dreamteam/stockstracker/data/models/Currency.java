package fhnw.dreamteam.stockstracker.data.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Currency {

    public Currency(){ }

    /**
     * The ID of the currentcy.
     */
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    /**
     * The name of the currency.
     */
    @Getter
    @Setter
    private String name;

    /**
     * The user which the currency belongs to.
     */
    @OneToOne
    @Setter
    @Getter
    @NotNull
    private User user;

    @Getter
    @Setter
    @OneToMany(mappedBy = "currency")
    private List<Stock> stocks;

    public Currency(
        @NotEmpty(message = "Please provide the name.") String name
    ) {
        this.name = name;
    }
}

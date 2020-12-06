package fhnw.dreamteam.stockstracker.data.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

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

    public Currency(
        @NotEmpty(message = "Please provide the name.") String name
    ) {
        this.name = name;
    }
}

package fhnw.dreamteam.stockstracker.data.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Currency {
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
}

package fhnw.dreamteam.stockstracker.data.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Stock {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Please provide a name.")
    private String name;

    @ManyToOne
    @JsonIgnore
    private Currency currency;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency agent) {
        this.currency = agent;
    }

}

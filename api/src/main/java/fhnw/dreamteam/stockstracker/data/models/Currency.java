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
    @Setter
    private Long id;

    /**
     * The name of the currency.
     */
    @Getter
    @Setter
    @NotEmpty(message = "Please provide a name.")
    private String name;

    @Getter
    @Setter
    @NotNull(message = "Please provide say if it is active or not.")
    private Boolean isActive;

    /**
     * The user which the currency belongs to.
     */
    @OneToOne
    @Setter
    @Getter
    @JsonIgnore
    private User user;

    @Getter
    @Setter
    @OneToMany(mappedBy = "currency")
    @JsonIgnore
    private List<Stock> stocks;

    public Currency(
        @NotEmpty(message = "Please provide the name.") String name,
        @NotEmpty(message = "Please provide information if currency is active or not.") Boolean isActive,
        @NotEmpty(message = "User not found.") User user
    ) {
        this.name = name;
        this.isActive = isActive;
        this.user = user;
    }

}

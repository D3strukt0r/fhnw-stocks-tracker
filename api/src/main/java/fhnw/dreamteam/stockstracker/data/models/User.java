package fhnw.dreamteam.stockstracker.data.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(unique=true)
    @NotEmpty(message = "Please enter a username.")
    private String username;

    @Getter
    @Setter
    @NotEmpty(message = "Please provide your firstname.")
    private String firstname;

    @Getter
    @Setter
    @NotEmpty(message = "Please provide your lastname.")
    private String lastname;

    @Getter
    @Setter
    @Email(message = "Please provide a valid e-mail.")
    @Column(unique=true)
    @NotEmpty(message = "Please provide an e-mail.")
    private String email;

    @Setter
    @org.springframework.data.annotation.Transient //will not be serialized
    private String password;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Stock> stocks;


    public String getPassword() {
        String transientPassword = this.password;
        this.password = null;
        return transientPassword;
    }
}

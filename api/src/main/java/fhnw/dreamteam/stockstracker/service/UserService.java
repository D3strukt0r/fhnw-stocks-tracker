package fhnw.dreamteam.stockstracker.service;

import fhnw.dreamteam.stockstracker.data.models.Currency;
import fhnw.dreamteam.stockstracker.data.models.User;
import fhnw.dreamteam.stockstracker.data.repository.CurrencyRepository;
import fhnw.dreamteam.stockstracker.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserService {
    /**
     * The user repository.
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    Validator validator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(@Valid final User user, CurrencyService currencyService) throws Exception {

        if (user.getId() == null) {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new Exception("Email address " + user.getEmail() + " is already assigned to another user.");
            }
            if (userRepository.findByUsername(user.getUsername()) != null) {
                throw new Exception("Username " + user.getUsername() + " is already assigned to another user.");
            }
        } else {
                if (userRepository.findByEmailAndIdNot(user.getEmail(), user.getId()) != null) {
                    throw new Exception("Email address " + user.getEmail() + " is already assigned to anther user.");
                }
                if (userRepository.findByUsernameAndIdNot(user.getUsername(), user.getId()) != null) {
                    throw new Exception("Username " + user.getUsername() + " is already assigned to another user.");
                }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Currency currency1 = new Currency("CHF", true, user);
        Currency currency2 = new Currency("USD", true, user);
        currencyService.createCurrency(currency1);
        currencyService.createCurrency(currency2);
    }

    /**
     * Edit an existing {@link User}.
     * @param user The user to be edited, with the new information.
     *
     * @return Returns the newly added user.
     *
     * @throws Exception
     */
    public User editUser(@Valid final User user) throws Exception {
        Optional<User> dbUser = userRepository.findById(user.getId());

        if(dbUser.isPresent() && getCurrentUser().getUsername() != dbUser.get().getUsername()) {
            throw new Exception("You are not permitted to edit this profile");
        }

        if (user.getId() != null && dbUser != null && dbUser.isPresent()) {
            dbUser.get().setUsername(user.getUsername());
            dbUser.get().setFirstname(user.getFirstname());
            dbUser.get().setLastname(user.getLastname());
            dbUser.get().setEmail(user.getEmail());
            dbUser.get().setMobile(user.getMobile());
            dbUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
        }
        //Exceptions
        if (userRepository.findByEmailAndIdNot(user.getEmail(), user.getId()) != null) {
            throw new Exception("Email address " + user.getEmail() + " is already assigned to anther user.");
        }
        if (userRepository.findByUsernameAndIdNot(user.getUsername(), user.getId()) != null) {
            throw new Exception("Username " + user.getUsername() + " is already assigned to another user.");
        }
        return userRepository.save(dbUser.get());
    }

    /**
     * Find all users.
     *
     * @return Returns all users.
     */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(username);
    }

}

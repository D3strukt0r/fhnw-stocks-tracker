package fhnw.dreamteam.stockstracker.data.seeddata;

import fhnw.dreamteam.stockstracker.data.models.User;
import fhnw.dreamteam.stockstracker.service.CurrencyService;
import fhnw.dreamteam.stockstracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSeed {

    @Autowired
    private UserService userService;

    @Autowired
    CurrencyService currencyService;

    public void seedUsers() throws Exception {
        User user1 = new User("testuser1", "test", "user1", "test1@gmail.com", "1890821481", "password");
        User user2 = new User("testuser2", "test", "user2", "test2@gmail.com", "8908213623", "password");
        User user3 = new User("testuser3", "test", "user3", "test3@gmail.com", "4721890325", "password");
        userService.createUser(user1, currencyService);
        userService.createUser(user2, currencyService);
        userService.createUser(user3, currencyService);
    }
}

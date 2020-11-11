package fhnw.dreamteam.userstracker.service;

import fhnw.dreamteam.userstracker.data.models.User;
import fhnw.dreamteam.userstracker.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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

    /**
     * Create a new {@link User}.
     *
     * @param user The user to be added
     *
     * @return Returns the newly added user.
     *
     * @throws Exception
     */
    public User createUser(@Valid final User user) throws Exception {
        return userRepository.save(user);
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
        if (user.getId() != null && dbUser != null && dbUser.isPresent()) {
            dbUser.get().setName(user.getName());
            return userRepository.save(dbUser.get());
        } else {
            throw new Exception("User could not be found.");
        }
    }

    /**
     * Deletes a {@link User}.
     *
     * @param userId The user's ID to delete.
     */
    public void deleteUser(final Long userId) {
        userRepository.deleteById(userId);
    }

    // /**
    //  * Find a user by it's ID.
    //  *
    //  * @param userId The {@link User}'s ID.
    //  *
    //  * @return Returns the user.
    //  *
    //  * @throws Exception Throws if nothing found.
    //  */
    // public User findUserById(final Long userId) throws Exception {
    //     List<User> userList = userRepository.findById(userId);
    //     if (userList.isEmpty()) {
    //         throw new Exception("No customer with ID " + userId + " found.");
    //     }
    //     return userList.get(0);
    // }

    /**
     * Find all users.
     *
     * @return Returns all users.
     */
    public List<User> findAllUsers() {
        return userRepository.getAllBy();
    }
}

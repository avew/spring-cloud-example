package id.aseprojali.resto.account.service;

import id.aseprojali.resto.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import id.aseprojali.resto.account.domain.User;

import java.util.Optional;

/**
 * Created by avew on 8/1/17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(String firstname, String lastname, String email) {
        User user = new User(firstname, lastname, email);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
}

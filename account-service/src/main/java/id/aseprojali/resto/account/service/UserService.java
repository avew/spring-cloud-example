package id.aseprojali.resto.account.service;

import id.aseprojali.resto.account.domain.User;

import java.util.Optional;

/**
 * Created by avew on 8/1/17.
 */
public interface UserService {

    User createUser(String firstname, String lastname, String email);

    Optional<User> findByEmail(String email);
}

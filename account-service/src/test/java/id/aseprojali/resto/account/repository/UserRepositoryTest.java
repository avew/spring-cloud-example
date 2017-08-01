package id.aseprojali.resto.account.repository;

import id.aseprojali.resto.account.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by avew on 8/1/17.
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindUserByEmail() throws Exception {

        User stubUser = getStubUser();
        userRepository.save(stubUser);

        Optional<User> byEmail = userRepository.findOneByEmail(stubUser.getEmail());
        assertTrue(byEmail.isPresent());

    }

    private User getStubUser() {
        return new User("Asep", "Rojali", "aseprojali@gmail.com");
    }

}
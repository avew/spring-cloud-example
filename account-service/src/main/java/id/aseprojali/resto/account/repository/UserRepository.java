package id.aseprojali.resto.account.repository;

import id.aseprojali.resto.account.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by avew on 8/1/17.
 */
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findOneByEmail(String email);

}

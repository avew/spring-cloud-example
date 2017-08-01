package id.aseprojali.resto.account.rest;

import com.netflix.ribbon.proxy.annotation.Http;
import id.aseprojali.resto.account.domain.User;
import id.aseprojali.resto.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by avew on 8/1/17.
 */
@RestController
public class UserResource {

    @Autowired
    private UserService userService;

    @RequestMapping(
            path = "/",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User u = userService.createUser(user.getFirstname(), user.getLastname(), user.getEmail());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        Optional<User> byEmail = userService.findByEmail(email);
        return byEmail
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

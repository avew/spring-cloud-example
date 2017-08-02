package id.aseprojali.resto.account.rest;

import id.aseprojali.resto.account.ManagedUserVM;
import id.aseprojali.resto.account.domain.User;
import id.aseprojali.resto.account.repository.UserRepository;
import id.aseprojali.resto.account.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by avew on 8/1/17.
 */
@RestController
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String CHECK_ERROR_MESSAGE = "Incorrect password";


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @return the ResponseEntity with status 201 (Created) if the user is registered or 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping(path = "/register",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            return new ResponseEntity<>(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
                .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
                                .map(user -> new ResponseEntity<>("email address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                                .orElseGet(() -> {
                                    User user = userService
                                            .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                                                    managedUserVM.getFirstName(), managedUserVM.getLastName(),
                                                    managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(),
                                                    managedUserVM.getLangKey());

//                            mailService.sendActivationEmail(user);
                                    return new ResponseEntity<>(HttpStatus.CREATED);
                                })
                );
    }
    
    private boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
                password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}

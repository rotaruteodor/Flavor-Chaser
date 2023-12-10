package teodor.flavor_chaser_spring_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.UserDto;
import teodor.flavor_chaser_spring_backend.entities.User;
import teodor.flavor_chaser_spring_backend.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class UsersController {

    private static final String USERS_MAIN_URL = "/users";
    private static final String USERS_ID_URL = USERS_MAIN_URL + "/{id}";
    private static final String USERS_EMAIL_URL = USERS_MAIN_URL + "/findByEmailAddress";
    private static final String USERS_CREDENTIALS_URL = USERS_MAIN_URL + "/findByCredentials";


    @Autowired
    private UserService userService;

    @GetMapping(USERS_MAIN_URL)
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping(USERS_ID_URL)
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping(USERS_EMAIL_URL)
    public ResponseEntity<UserDto> getByEmailAddress(@RequestParam String emailAddress) {
        return userService.getByEmailAddress(emailAddress);
    }

    @GetMapping(USERS_CREDENTIALS_URL)
    public ResponseEntity<UserDto> getByCredentials(@RequestParam String emailAddress,
                                                    @RequestParam String password) {
        return userService.getByCredentials(emailAddress, password);
    }

    @PostMapping(USERS_MAIN_URL)
    public ResponseEntity<UserDto> add(@RequestBody User user) {
        return userService.add(user);
    }

}

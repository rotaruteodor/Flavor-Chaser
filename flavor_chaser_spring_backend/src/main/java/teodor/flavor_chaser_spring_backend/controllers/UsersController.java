package teodor.flavor_chaser_spring_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.RecipeFlavorDto;
import teodor.flavor_chaser_spring_backend.dtos.UserDto;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.entities.User;
import teodor.flavor_chaser_spring_backend.services.UserService;

import javax.management.MBeanAttributeInfo;
import java.util.List;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class UsersController {

    private static final String USERS_MAIN_URL = "/users";
    private static final String USERS_ID_URL = USERS_MAIN_URL + "/{id}";

    @Autowired
    private UserService userService;

    @GetMapping(USERS_MAIN_URL)
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @PostMapping(USERS_MAIN_URL)
    public ResponseEntity<UserDto> add(@RequestBody User user) {
        return userService.add(user);
    }

}

package teodor.flavor_chaser_spring_backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.IngredientInStashDto;
import teodor.flavor_chaser_spring_backend.entities.IngredientInStash;
import teodor.flavor_chaser_spring_backend.services.IngredientInStashService;

import java.util.List;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class IngredientsInStashController {

    private static final String INGREDIENTS_IN_STASH_MAIN_URL = "/ingredients_in_stash";
    private static final String INGREDIENTS_IN_STASH_ID_URL = INGREDIENTS_IN_STASH_MAIN_URL + "/{id}";

    private static final String INGREDIENTS_IN_STASH_USER_URL = INGREDIENTS_IN_STASH_MAIN_URL + "/users/{userId}";

    @Autowired
    private IngredientInStashService ingredientInStashService;

    @GetMapping(INGREDIENTS_IN_STASH_MAIN_URL)
    public List<IngredientInStashDto> getAll() {
        return ingredientInStashService.getAll();
    }

    @GetMapping(INGREDIENTS_IN_STASH_ID_URL)
    public ResponseEntity<IngredientInStashDto> getById(@PathVariable Long id) {
        return ingredientInStashService.getById(id);
    }

    @GetMapping(INGREDIENTS_IN_STASH_USER_URL)
    public List<IngredientInStashDto> getAllByUserId(@PathVariable Long userId) {
        return ingredientInStashService.getAllByUserId(userId);
    }

    @PostMapping(INGREDIENTS_IN_STASH_MAIN_URL)
    public ResponseEntity<IngredientInStashDto> add(@RequestBody IngredientInStash ingredientInStash) {
        return ingredientInStashService.add(ingredientInStash);
    }
}

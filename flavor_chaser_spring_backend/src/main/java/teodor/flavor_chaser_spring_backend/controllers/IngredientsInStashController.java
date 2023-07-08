package teodor.flavor_chaser_spring_backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.FlavorWarningDto;
import teodor.flavor_chaser_spring_backend.dtos.IngredientInStashDto;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;
import teodor.flavor_chaser_spring_backend.entities.IngredientInStash;
import teodor.flavor_chaser_spring_backend.services.FlavorWarningService;
import teodor.flavor_chaser_spring_backend.services.IngredientInStashService;

import java.util.List;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class IngredientsInStashController {

    private static final String INGREDIENTS_IN_STASH_MAIN_URL = "/ingredients_in_stash";
    private static final String INGREDIENTS_IN_STASH_ID_URL = INGREDIENTS_IN_STASH_MAIN_URL + "/{id}";

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

    @PostMapping(INGREDIENTS_IN_STASH_MAIN_URL)
    public ResponseEntity<IngredientInStashDto> add(@RequestBody IngredientInStash ingredientInStash) {
        return ingredientInStashService.add(ingredientInStash);
    }
}

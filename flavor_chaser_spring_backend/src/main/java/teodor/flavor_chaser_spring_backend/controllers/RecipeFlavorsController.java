package teodor.flavor_chaser_spring_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.FlavorWarningDto;
import teodor.flavor_chaser_spring_backend.dtos.RecipeFlavorDto;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.services.RecipeFlavorService;

import java.util.List;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class RecipeFlavorsController {

    private static final String RECIPE_FLAVORS_MAIN_URL = "/recipe_flavors";
    private static final String RECIPE_FLAVORS_ID_URL = RECIPE_FLAVORS_MAIN_URL + "/{id}";

    @Autowired
    private RecipeFlavorService recipeFlavorService;

    @GetMapping(RECIPE_FLAVORS_MAIN_URL)
    public List<RecipeFlavorDto> getAll() {
        return recipeFlavorService.getAll();
    }

    @PostMapping(RECIPE_FLAVORS_MAIN_URL)
    public ResponseEntity<RecipeFlavorDto> add(@RequestBody RecipeFlavor recipeFlavor) {
        return recipeFlavorService.add(recipeFlavor);
    }
}

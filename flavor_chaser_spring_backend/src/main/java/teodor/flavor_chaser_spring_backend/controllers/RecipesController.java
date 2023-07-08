package teodor.flavor_chaser_spring_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.RecipeDto;
import teodor.flavor_chaser_spring_backend.entities.Recipe;
import teodor.flavor_chaser_spring_backend.services.RecipeService;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/flavor-chaser-backend")
public class RecipesController {

    private static final String RECIPES_MAIN_URL = "/recipes";
    private static final String RECIPES_ID_URL = RECIPES_MAIN_URL + "/{id}";

    @Autowired
    private RecipeService recipeService;

    @GetMapping(RECIPES_MAIN_URL)
    public List<RecipeDto> getAll() {
        return recipeService.getAll();
    }

    @GetMapping(RECIPES_ID_URL)
    public ResponseEntity<RecipeDto> getById(@PathVariable Long id) {
        return recipeService.getById(id);
    }

    @PostMapping(RECIPES_MAIN_URL)
    public ResponseEntity<RecipeDto> add(
            @RequestBody Recipe recipe) {
        return recipeService.add(recipe);
    }

}

package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.RecipeDto;
import teodor.flavor_chaser_spring_backend.entities.Recipe;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.exceptions.ErrorMessage;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;
import teodor.flavor_chaser_spring_backend.mappers.RecipeMapper;
import teodor.flavor_chaser_spring_backend.repositories.FlavorsRepository;
import teodor.flavor_chaser_spring_backend.repositories.RecipeFlavorsRepository;
import teodor.flavor_chaser_spring_backend.repositories.RecipesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipesRepository recipesRepository;

    @Autowired
    private RecipeFlavorsRepository recipeFlavorsRepository;

    @Autowired
    private FlavorsRepository flavorsRepository;


    @Autowired
    private RecipeMapper recipeMapper;

    public List<RecipeDto> getAll() {
        return recipesRepository.findAll()
                .stream()
                .map(r -> recipeMapper.toDto(r))
                .toList();
    }

    public ResponseEntity<RecipeDto> getById(@PathVariable Long id) {
        return recipesRepository.findById(id)
                .map(r -> recipeMapper.toDto(r))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getDoesNotExistErrorMessage("RecipeInfo", id)));
    }

    public ResponseEntity<RecipeDto> add(@RequestBody Recipe recipe) {

        for (RecipeFlavor recipeFlavor : recipe.getRecipeFlavors()) {
            flavorsRepository.findById(recipeFlavor.getFlavor().getId())
                    .map(flavor -> {
                        recipeFlavor.setFlavor(flavor);
                        return flavor;
                    })
                    .orElseThrow(() -> new ResourceNotFoundException("Flavor with ID:"
                            + recipeFlavor.getFlavor().getId()
                            + " WAS NOT FOUND"));
        }

        recipesRepository.save(recipe);
        return ResponseEntity.ok(recipeMapper.toDto(recipe));
    }
}

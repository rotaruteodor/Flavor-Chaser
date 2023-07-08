package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.IngredientInStashDto;
import teodor.flavor_chaser_spring_backend.dtos.RecipeFlavorDto;
import teodor.flavor_chaser_spring_backend.entities.IngredientInStash;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.exceptions.ErrorMessage;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;
import teodor.flavor_chaser_spring_backend.mappers.IngredientInStashMapper;
import teodor.flavor_chaser_spring_backend.repositories.FlavorsRepository;
import teodor.flavor_chaser_spring_backend.repositories.IngredientsInStashRepository;

import java.util.List;

@Service
public class IngredientInStashService {

    @Autowired
    IngredientsInStashRepository ingredientsInStashRepository;

    @Autowired
    FlavorsRepository flavorsRepository;

    @Autowired
    IngredientInStashMapper ingredientInStashMapper;

    public List<IngredientInStashDto> getAll() {
        return ingredientsInStashRepository.findAll()
                .stream()
                .map(r -> ingredientInStashMapper.toDto(r))
                .toList();
    }

    public ResponseEntity<IngredientInStashDto> getById(@PathVariable Long id) {
        return ingredientsInStashRepository.findById(id)
                .map(r -> ingredientInStashMapper.toDto(r))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getDoesNotExistErrorMessage("IngredientInStash", id)));
    }

    public ResponseEntity<IngredientInStashDto> add(@RequestBody IngredientInStash ingredientInStash) {
        flavorsRepository.findById(ingredientInStash.getFlavor().getId())
                .map(w -> {
                    ingredientInStash.setFlavor(w);
                    return w;
                }).orElseThrow(() -> new ResourceNotFoundException("Flavor with ID:"
                        + ingredientInStash.getFlavor().getId()
                        + " WAS NOT FOUND"));

        ingredientsInStashRepository.save(ingredientInStash);
        return ResponseEntity.ok(ingredientInStashMapper.toDto(ingredientInStash));
    }
}



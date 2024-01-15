package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.IngredientInStashDto;
import teodor.flavor_chaser_spring_backend.entities.IngredientInStash;
import teodor.flavor_chaser_spring_backend.entities.enums.MainIngredientType;
import teodor.flavor_chaser_spring_backend.exceptions.ErrorMessage;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;
import teodor.flavor_chaser_spring_backend.mappers.IngredientInStashMapper;
import teodor.flavor_chaser_spring_backend.repositories.FlavorsRepository;
import teodor.flavor_chaser_spring_backend.repositories.IngredientsInStashRepository;
import teodor.flavor_chaser_spring_backend.repositories.UsersRepository;

import java.util.List;

@Service
public class IngredientInStashService {

    @Autowired
    IngredientsInStashRepository ingredientsInStashRepository;

    @Autowired
    FlavorsRepository flavorsRepository;

    @Autowired
    UsersRepository usersRepository;

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

    public List<IngredientInStashDto> getAllByUserId(@PathVariable Long userId) {
        return ingredientsInStashRepository.findAllByUserId(userId)
                .stream()
                .map(r -> ingredientInStashMapper.toDto(r))
                .toList();
    }

    public ResponseEntity<IngredientInStashDto> add(@RequestBody IngredientInStash ingredientInStash) {
        if (ingredientInStash.getType() == MainIngredientType.FLAVOR) {
            flavorsRepository.findById(ingredientInStash.getFlavor().getId())
                    .map(w -> {
                        ingredientInStash.setFlavor(w);
                        return w;
                    }).orElseThrow(() -> new ResourceNotFoundException("Flavor with ID:"
                            + ingredientInStash.getFlavor().getId()
                            + " WAS NOT FOUND"));
        } else {
            ingredientInStash.setFlavor(null); // TODO Do I need this?
        }

        usersRepository.findById(ingredientInStash.getUser().getId())
                .map(w -> {
                    ingredientInStash.setUser(w);
                    return w;
                }).orElseThrow(() -> new ResourceNotFoundException("User with ID:"
                        + ingredientInStash.getUser().getId()
                        + " WAS NOT FOUND"));

        ingredientsInStashRepository.save(ingredientInStash);
        return ResponseEntity.ok(ingredientInStashMapper.toDto(ingredientInStash));
    }
}



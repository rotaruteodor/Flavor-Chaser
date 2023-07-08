package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.FlavorWarningDto;
import teodor.flavor_chaser_spring_backend.dtos.RecipeFlavorDto;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;
import teodor.flavor_chaser_spring_backend.entities.Recipe;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.exceptions.ErrorMessage;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;
import teodor.flavor_chaser_spring_backend.mappers.RecipeFlavorMapper;
import teodor.flavor_chaser_spring_backend.repositories.FlavorsRepository;
import teodor.flavor_chaser_spring_backend.repositories.RecipeFlavorsRepository;

import java.util.List;

@Service
public class RecipeFlavorService {

    @Autowired
    private RecipeFlavorsRepository recipeFlavorsRepository;

    @Autowired
    private FlavorsRepository flavorsRepository;

    @Autowired
    private RecipeFlavorMapper recipeFlavorMapper;


    public List<RecipeFlavorDto> getAll() {
        return recipeFlavorsRepository.findAll()
                .stream()
                .map(r -> recipeFlavorMapper.toDto(r))
                .toList();
    }

    public ResponseEntity<RecipeFlavorDto> getById(@PathVariable Long id) {
        return recipeFlavorsRepository.findById(id)
                .map(r -> recipeFlavorMapper.toDto(r))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getDoesNotExistErrorMessage("RecipeFlavor", id)));
    }

    public ResponseEntity<RecipeFlavorDto> add(@RequestBody RecipeFlavor recipeFlavor) {
        flavorsRepository.findById(recipeFlavor.getFlavor().getId())
                .map(w -> {
                    recipeFlavor.setFlavor(w);
                    return w;
                }).orElseThrow(() -> new ResourceNotFoundException("Flavor with ID:"
                        + recipeFlavor.getFlavor().getId()
                        + " WAS NOT FOUND"));

        recipeFlavorsRepository.save(recipeFlavor);
        return ResponseEntity.ok(recipeFlavorMapper.toDto(recipeFlavor));
    }
}

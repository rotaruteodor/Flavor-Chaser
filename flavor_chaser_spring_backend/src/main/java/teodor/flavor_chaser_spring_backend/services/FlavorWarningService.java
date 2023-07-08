package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.FlavorDto;
import teodor.flavor_chaser_spring_backend.dtos.FlavorWarningDto;
import teodor.flavor_chaser_spring_backend.entities.Flavor;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.exceptions.ErrorMessage;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;
import teodor.flavor_chaser_spring_backend.mappers.FlavorWarningMapper;
import teodor.flavor_chaser_spring_backend.mappers.RecipeFlavorMapper;
import teodor.flavor_chaser_spring_backend.repositories.FlavorWarningsRepository;
import teodor.flavor_chaser_spring_backend.repositories.RecipeFlavorsRepository;

import java.util.List;

@Service
public class FlavorWarningService {


    @Autowired
    private FlavorWarningsRepository flavorWarningsRepository;

    @Autowired
    private FlavorWarningMapper flavorWarningMapper;

    public List<FlavorWarningDto> getAll() {
        return flavorWarningsRepository.findAll()
                .stream()
                .map(f -> flavorWarningMapper.toDto(f))
                .toList();
    }

    public ResponseEntity<FlavorWarningDto> getById(@PathVariable Long id) {
        return flavorWarningsRepository.findById(id)
                .map(f -> flavorWarningMapper.toDto(f))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getDoesNotExistErrorMessage("FlavorWarning", id)));
    }

    public ResponseEntity<FlavorWarningDto> add(@RequestBody FlavorWarning flavorWarning) {


        flavorWarningsRepository.save(flavorWarning);
        return ResponseEntity.ok(flavorWarningMapper.toDto(flavorWarning));
    }
}

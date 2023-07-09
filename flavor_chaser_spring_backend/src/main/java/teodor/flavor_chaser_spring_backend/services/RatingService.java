package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.RatingDto;
import teodor.flavor_chaser_spring_backend.dtos.RecipeFlavorDto;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.exceptions.ErrorMessage;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;
import teodor.flavor_chaser_spring_backend.mappers.RatingMapper;
import teodor.flavor_chaser_spring_backend.repositories.RatingsRepository;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingsRepository ratingsRepository;

    @Autowired
    private RatingMapper ratingMapper;

    public List<RatingDto> getAll() {
        return ratingsRepository.findAll()
                .stream()
                .map(r -> ratingMapper.toDto(r))
                .toList();
    }

    public ResponseEntity<RatingDto> getById(@PathVariable Long id) {
        return ratingsRepository.findById(id)
                .map(r -> ratingMapper.toDto(r))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getDoesNotExistErrorMessage("Rating", id)));
    }
}

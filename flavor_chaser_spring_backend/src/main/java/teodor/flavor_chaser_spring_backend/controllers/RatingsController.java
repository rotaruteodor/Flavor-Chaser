package teodor.flavor_chaser_spring_backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.IngredientInStashDto;
import teodor.flavor_chaser_spring_backend.dtos.RatingDto;
import teodor.flavor_chaser_spring_backend.entities.IngredientInStash;
import teodor.flavor_chaser_spring_backend.entities.Rating;
import teodor.flavor_chaser_spring_backend.services.IngredientInStashService;
import teodor.flavor_chaser_spring_backend.services.RatingService;

import java.util.List;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class RatingsController {

    private static final String RATINGS_MAIN_URL = "/ratings";
    private static final String RATINGS_ID_URL = RATINGS_MAIN_URL + "/{id}";

    @Autowired
    private RatingService ratingService;

    @GetMapping(RATINGS_MAIN_URL)
    public List<RatingDto> getAll() {
        return ratingService.getAll();
    }

    @GetMapping(RATINGS_ID_URL)
    public ResponseEntity<RatingDto> getById(@PathVariable Long id) {
        return ratingService.getById(id);
    }

    @PostMapping(RATINGS_MAIN_URL)
    public ResponseEntity<RatingDto> add(@RequestBody Rating rating) {
        return ratingService.add(rating);
    }
}

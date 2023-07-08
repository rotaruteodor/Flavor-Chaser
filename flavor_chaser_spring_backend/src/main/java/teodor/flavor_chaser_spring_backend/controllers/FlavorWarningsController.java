package teodor.flavor_chaser_spring_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.FlavorWarningDto;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;
import teodor.flavor_chaser_spring_backend.services.FlavorWarningService;

import java.util.List;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class FlavorWarningsController {


    private static final String FLAVOR_WARNINGS_MAIN_URL = "/flavor_warnings";
    private static final String FLAVOR_WARNINGS_ID_URL = FLAVOR_WARNINGS_MAIN_URL + "/{id}";

    @Autowired
    private FlavorWarningService flavorWarningService;

    @GetMapping(FLAVOR_WARNINGS_MAIN_URL)
    public List<FlavorWarningDto> getAll() {
        return flavorWarningService.getAll();
    }

    @GetMapping(FLAVOR_WARNINGS_ID_URL)
    public ResponseEntity<FlavorWarningDto> getById(@PathVariable Long id) {
        return flavorWarningService.getById(id);
    }

    @PostMapping(FLAVOR_WARNINGS_MAIN_URL)
    public ResponseEntity<FlavorWarningDto> add(@RequestBody FlavorWarning flavorWarning) {
        return flavorWarningService.add(flavorWarning);
    }
}

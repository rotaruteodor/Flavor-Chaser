package teodor.flavor_chaser_spring_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.FlavorDto;
import teodor.flavor_chaser_spring_backend.entities.Flavor;
import teodor.flavor_chaser_spring_backend.services.FlavorService;

import java.util.List;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class FlavorsController {

    private static final String FLAVORS_MAIN_URL = "/flavors";
    private static final String FLAVORS_ID_URL = FLAVORS_MAIN_URL + "/{id}";

    @Autowired
    private FlavorService flavorService;

    @GetMapping(FLAVORS_MAIN_URL)
    public List<FlavorDto> getAll() {
        return flavorService.getAll();
    }

    @GetMapping(FLAVORS_ID_URL)
    public ResponseEntity<FlavorDto> getById(@PathVariable Long id) {
        return flavorService.getById(id);
    }

    @PostMapping(FLAVORS_MAIN_URL)
    public ResponseEntity<FlavorDto> add(@RequestBody Flavor flavor) {
        return flavorService.add(flavor);
    }

}
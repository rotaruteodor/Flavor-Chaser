package teodor.flavor_chaser_spring_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.CompanyDto;
import teodor.flavor_chaser_spring_backend.dtos.FlavorCategoryDto;
import teodor.flavor_chaser_spring_backend.entities.Company;
import teodor.flavor_chaser_spring_backend.entities.FlavorCategory;
import teodor.flavor_chaser_spring_backend.services.FlavorCategoryService;

import javax.management.MBeanAttributeInfo;
import java.util.List;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class FlavorCategoriesController {

    private static final String FLAVOR_CATEGORIES_MAIN_URL = "/flavor_categories";
    private static final String FLAVOR_CATEGORIES_ID_URL = FLAVOR_CATEGORIES_MAIN_URL + "/{id}";

    @Autowired
    private FlavorCategoryService flavorCategoryService;

    @GetMapping(FLAVOR_CATEGORIES_MAIN_URL)
    public List<FlavorCategoryDto> getAll() {
        return flavorCategoryService.getAll();
    }

    @GetMapping(FLAVOR_CATEGORIES_ID_URL)
    public ResponseEntity<FlavorCategoryDto> getById(@PathVariable Long id) {
        return flavorCategoryService.getById(id);
    }

    @PostMapping(FLAVOR_CATEGORIES_MAIN_URL)
    public ResponseEntity<FlavorCategoryDto> add(@RequestBody FlavorCategory flavorCategory) {
        return flavorCategoryService.add(flavorCategory);
    }


}

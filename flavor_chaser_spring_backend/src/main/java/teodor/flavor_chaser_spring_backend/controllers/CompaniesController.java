package teodor.flavor_chaser_spring_backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teodor.flavor_chaser_spring_backend.dtos.CompanyDto;
import teodor.flavor_chaser_spring_backend.dtos.RecipeFlavorDto;
import teodor.flavor_chaser_spring_backend.entities.Company;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.services.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class CompaniesController {

    private static final String COMPANIES_MAIN_URL = "/companies";
    private static final String COMPANIES_ID_URL = COMPANIES_MAIN_URL + "/{id}";

    @Autowired
    private CompanyService companyService;

    @GetMapping(COMPANIES_MAIN_URL)
    public List<CompanyDto> getAll() {
        return companyService.getAll();
    }

    @GetMapping(COMPANIES_ID_URL)
    public ResponseEntity<CompanyDto> getById(@PathVariable Long id) {
        return companyService.getById(id);
    }

    @PostMapping(COMPANIES_MAIN_URL)
    public ResponseEntity<CompanyDto> add(@RequestBody Company company) {
        return companyService.add(company);
    }

}

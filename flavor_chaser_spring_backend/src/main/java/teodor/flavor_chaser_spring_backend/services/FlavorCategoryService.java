package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.CompanyDto;
import teodor.flavor_chaser_spring_backend.dtos.FlavorCategoryDto;
import teodor.flavor_chaser_spring_backend.entities.Company;
import teodor.flavor_chaser_spring_backend.entities.FlavorCategory;
import teodor.flavor_chaser_spring_backend.exceptions.ErrorMessage;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;
import teodor.flavor_chaser_spring_backend.mappers.FlavorCategoryMapper;
import teodor.flavor_chaser_spring_backend.repositories.FlavorCategoriesRepository;

import java.util.List;

@Service
public class FlavorCategoryService {

    @Autowired
    private FlavorCategoriesRepository flavorCategoriesRepository;

    @Autowired
    private FlavorCategoryMapper flavorCategoryMapper;

    public List<FlavorCategoryDto> getAll() {
        return flavorCategoriesRepository.findAll()
                .stream()
                .map(f -> flavorCategoryMapper.toDto(f))
                .toList();
    }

    public ResponseEntity<FlavorCategoryDto> getById(@PathVariable Long id) {
        return flavorCategoriesRepository.findById(id)
                .map(f -> flavorCategoryMapper.toDto(f))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getDoesNotExistErrorMessage("FlavorCategory", id)));
    }

    public ResponseEntity<FlavorCategoryDto> add(@RequestBody FlavorCategory flavorCategory) {
        flavorCategoriesRepository.save(flavorCategory);
        return ResponseEntity.ok(flavorCategoryMapper.toDto(flavorCategory));
    }
}

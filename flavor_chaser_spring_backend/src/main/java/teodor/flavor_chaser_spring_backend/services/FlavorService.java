package teodor.flavor_chaser_spring_backend.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.FlavorDto;
import teodor.flavor_chaser_spring_backend.entities.Flavor;
import teodor.flavor_chaser_spring_backend.exceptions.ErrorMessage;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;
import teodor.flavor_chaser_spring_backend.mappers.FlavorMapper;
import teodor.flavor_chaser_spring_backend.repositories.CompaniesRepository;
import teodor.flavor_chaser_spring_backend.repositories.FlavorCategoriesRepository;
import teodor.flavor_chaser_spring_backend.repositories.FlavorWarningsRepository;
import teodor.flavor_chaser_spring_backend.repositories.FlavorsRepository;

import java.util.List;

@Service
public class FlavorService {

    @Autowired
    private FlavorsRepository flavorsRepository;

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private FlavorCategoriesRepository flavorCategoriesRepository;

    @Autowired
    private FlavorWarningsRepository flavorWarningsRepository;

    @Autowired
    private FlavorMapper flavorMapper;


    @Transactional
    public List<FlavorDto> getAll() {
        return flavorsRepository.findAll()
                .stream()
                .map(f -> flavorMapper.toDto(f))
                .toList();
    }

    public ResponseEntity<FlavorDto> getById(@PathVariable Long id) {
        return flavorsRepository.findById(id)
                .map(f -> flavorMapper.toDto(f))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getDoesNotExistErrorMessage("Flavor", id)));
    }

    public List<String> getAllNames() {
        return flavorsRepository.findAll()
                .stream()
                .map(Flavor::getName)
                .toList();
    }

    public ResponseEntity<FlavorDto> add(@RequestBody Flavor flavor) {

        companiesRepository.findById(flavor.getCompany().getId())
                .map(c -> {
                    flavor.setCompany(c);
                    return c;
                }).orElseThrow(() -> new ResourceNotFoundException("Company with ID:"
                        + flavor.getCompany().getId()
                        + " WAS NOT FOUND"));

        flavorCategoriesRepository.findById(flavor.getCategory().getId())
                .map(fc -> {
                    flavor.setCategory(fc);
                    return fc;
                }).orElseThrow(() -> new ResourceNotFoundException("FlavorCategory with ID:"
                        + flavor.getCategory().getId()
                        + " WAS NOT FOUND"));

        flavorWarningsRepository.findById(flavor.getWarning().getId())
                .map(w -> {
                    flavor.setWarning(w);
                    return w;
                }).orElseThrow(() -> new ResourceNotFoundException("FlavorWarning with ID:"
                        + flavor.getWarning().getId()
                        + " WAS NOT FOUND"));

        flavorsRepository.save(flavor);
        return ResponseEntity.ok(flavorMapper.toDto(flavor));
    }
}

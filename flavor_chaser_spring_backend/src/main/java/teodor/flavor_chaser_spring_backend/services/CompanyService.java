package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.CompanyDto;
import teodor.flavor_chaser_spring_backend.dtos.FlavorDto;
import teodor.flavor_chaser_spring_backend.entities.Company;
import teodor.flavor_chaser_spring_backend.entities.Flavor;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.exceptions.ErrorMessage;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;
import teodor.flavor_chaser_spring_backend.mappers.CompanyMapper;
import teodor.flavor_chaser_spring_backend.repositories.CompaniesRepository;

import java.util.List;

@Service
public class CompanyService {


    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private CompanyMapper companyMapper;


    public List<CompanyDto> getAll() {
        return companiesRepository.findAll()
                .stream()
                .map(f -> companyMapper.toDto(f))
                .toList();
    }

    public ResponseEntity<CompanyDto> getById(@PathVariable Long id) {
        return companiesRepository.findById(id)
                .map(f -> companyMapper.toDto(f))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getDoesNotExistErrorMessage("Company", id)));
    }

    public ResponseEntity<CompanyDto> add(@RequestBody Company company) {
        companiesRepository.save(company);
        return ResponseEntity.ok(companyMapper.toDto(company));
    }
}

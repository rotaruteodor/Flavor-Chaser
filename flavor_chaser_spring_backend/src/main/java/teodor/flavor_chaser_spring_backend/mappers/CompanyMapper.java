package teodor.flavor_chaser_spring_backend.mappers;

import org.mapstruct.Mapper;
import teodor.flavor_chaser_spring_backend.dtos.CompanyDto;
import teodor.flavor_chaser_spring_backend.entities.Company;

@Mapper
public interface CompanyMapper {

    CompanyDto toDto(Company company);
}

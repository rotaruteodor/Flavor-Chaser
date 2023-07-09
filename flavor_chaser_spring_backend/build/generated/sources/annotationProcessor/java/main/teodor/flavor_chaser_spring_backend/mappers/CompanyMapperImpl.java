package teodor.flavor_chaser_spring_backend.mappers;

import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.CompanyDto;
import teodor.flavor_chaser_spring_backend.entities.Company;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-09T11:36:59+0300",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
*/
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyDto toDto(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyDto companyDto = new CompanyDto();

        companyDto.setId( company.getId() );
        companyDto.setName( company.getName() );
        companyDto.setWebsite( company.getWebsite() );

        return companyDto;
    }
}

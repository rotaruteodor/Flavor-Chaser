package teodor.flavor_chaser_spring_backend.mappers;

import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.FlavorCategoryDto;
import teodor.flavor_chaser_spring_backend.entities.FlavorCategory;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-09T11:36:59+0300",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
*/
@Component
public class FlavorCategoryMapperImpl implements FlavorCategoryMapper {

    @Override
    public FlavorCategoryDto toDto(FlavorCategory flavorCategory) {
        if ( flavorCategory == null ) {
            return null;
        }

        FlavorCategoryDto flavorCategoryDto = new FlavorCategoryDto();

        flavorCategoryDto.setId( flavorCategory.getId() );
        flavorCategoryDto.setName( flavorCategory.getName() );

        return flavorCategoryDto;
    }
}

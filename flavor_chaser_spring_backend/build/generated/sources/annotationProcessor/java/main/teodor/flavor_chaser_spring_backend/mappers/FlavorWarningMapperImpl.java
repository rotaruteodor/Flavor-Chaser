package teodor.flavor_chaser_spring_backend.mappers;

import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.FlavorWarningDto;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-09T11:36:59+0300",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
*/
@Component
public class FlavorWarningMapperImpl implements FlavorWarningMapper {

    @Override
    public FlavorWarningDto toDto(FlavorWarning flavorWarning) {
        if ( flavorWarning == null ) {
            return null;
        }

        FlavorWarningDto flavorWarningDto = new FlavorWarningDto();

        flavorWarningDto.setId( flavorWarning.getId() );
        flavorWarningDto.setDescription( flavorWarning.getDescription() );

        return flavorWarningDto;
    }
}

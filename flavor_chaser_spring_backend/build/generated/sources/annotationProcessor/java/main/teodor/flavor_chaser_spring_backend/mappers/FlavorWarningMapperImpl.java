package teodor.flavor_chaser_spring_backend.mappers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.FlavorWarningDto;
import teodor.flavor_chaser_spring_backend.entities.Flavor;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T19:24:09+0300",
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
        List<Flavor> list = flavorWarning.getFlavors();
        if ( list != null ) {
            flavorWarningDto.setFlavors( new ArrayList<Flavor>( list ) );
        }
        else {
            flavorWarningDto.setFlavors( null );
        }

        return flavorWarningDto;
    }
}

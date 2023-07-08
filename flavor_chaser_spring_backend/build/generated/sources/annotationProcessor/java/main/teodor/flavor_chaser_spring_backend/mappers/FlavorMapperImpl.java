package teodor.flavor_chaser_spring_backend.mappers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.FlavorDto;
import teodor.flavor_chaser_spring_backend.entities.Flavor;
import teodor.flavor_chaser_spring_backend.entities.Rating;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T19:24:09+0300",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
*/
@Component
public class FlavorMapperImpl implements FlavorMapper {

    @Override
    public FlavorDto toDto(Flavor flavor) {
        if ( flavor == null ) {
            return null;
        }

        FlavorDto flavorDto = new FlavorDto();

        flavorDto.setId( flavor.getId() );
        flavorDto.setName( flavor.getName() );
        flavorDto.setDescription( flavor.getDescription() );
        flavorDto.setCompany( flavor.getCompany() );
        flavorDto.setCategory( flavor.getCategory() );
        flavorDto.setWarning( flavor.getWarning() );
        List<Rating> list = flavor.getRatings();
        if ( list != null ) {
            flavorDto.setRatings( new ArrayList<Rating>( list ) );
        }
        else {
            flavorDto.setRatings( null );
        }

        return flavorDto;
    }
}

package teodor.flavor_chaser_spring_backend.mappers;

import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.RatingDto;
import teodor.flavor_chaser_spring_backend.entities.Rating;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-09T11:36:59+0300",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
*/
@Component
public class RatingMapperImpl implements RatingMapper {

    @Override
    public RatingDto toDto(Rating rating) {
        if ( rating == null ) {
            return null;
        }

        RatingDto ratingDto = new RatingDto();

        ratingDto.setId( rating.getId() );
        ratingDto.setScore( rating.getScore() );

        return ratingDto;
    }
}

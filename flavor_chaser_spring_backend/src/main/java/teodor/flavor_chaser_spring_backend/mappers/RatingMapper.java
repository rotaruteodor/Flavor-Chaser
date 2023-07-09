package teodor.flavor_chaser_spring_backend.mappers;

import org.mapstruct.Mapper;
import teodor.flavor_chaser_spring_backend.dtos.RatingDto;
import teodor.flavor_chaser_spring_backend.entities.Rating;

@Mapper
public interface RatingMapper {

    RatingDto toDto(Rating rating);
}

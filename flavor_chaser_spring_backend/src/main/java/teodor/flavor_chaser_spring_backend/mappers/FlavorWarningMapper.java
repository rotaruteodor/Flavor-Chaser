package teodor.flavor_chaser_spring_backend.mappers;

import org.mapstruct.Mapper;
import teodor.flavor_chaser_spring_backend.dtos.FlavorWarningDto;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;

@Mapper
public interface FlavorWarningMapper {

    FlavorWarningDto toDto(FlavorWarning flavorWarning);
}

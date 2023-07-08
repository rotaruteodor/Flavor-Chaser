package teodor.flavor_chaser_spring_backend.mappers;

import org.mapstruct.Mapper;
import teodor.flavor_chaser_spring_backend.dtos.FlavorCategoryDto;
import teodor.flavor_chaser_spring_backend.entities.FlavorCategory;

@Mapper
public interface FlavorCategoryMapper {

    FlavorCategoryDto toDto(FlavorCategory flavorCategory);
}

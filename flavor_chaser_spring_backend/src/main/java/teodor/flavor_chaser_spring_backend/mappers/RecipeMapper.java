package teodor.flavor_chaser_spring_backend.mappers;

import org.mapstruct.Mapper;
import teodor.flavor_chaser_spring_backend.dtos.RecipeDto;
import teodor.flavor_chaser_spring_backend.entities.Recipe;

@Mapper
public interface RecipeMapper {
    RecipeDto toDto(Recipe recipe);
}
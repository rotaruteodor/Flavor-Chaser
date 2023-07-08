package teodor.flavor_chaser_spring_backend.mappers;

import org.mapstruct.Mapper;
import teodor.flavor_chaser_spring_backend.dtos.IngredientInStashDto;
import teodor.flavor_chaser_spring_backend.entities.IngredientInStash;

@Mapper
public interface IngredientInStashMapper {

    IngredientInStashDto toDto(IngredientInStash ingredientInStash);
}

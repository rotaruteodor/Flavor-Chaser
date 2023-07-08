package teodor.flavor_chaser_spring_backend.mappers;

import org.mapstruct.Mapper;
import teodor.flavor_chaser_spring_backend.dtos.RecipeFlavorDto;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;

@Mapper
public interface RecipeFlavorMapper {

    RecipeFlavorDto toDto(RecipeFlavor recipeFlavor);
}

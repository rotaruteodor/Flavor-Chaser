package teodor.flavor_chaser_spring_backend.mappers;

import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.RecipeFlavorDto;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T19:24:09+0300",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
*/
@Component
public class RecipeFlavorMapperImpl implements RecipeFlavorMapper {

    @Override
    public RecipeFlavorDto toDto(RecipeFlavor recipeFlavor) {
        if ( recipeFlavor == null ) {
            return null;
        }

        RecipeFlavorDto recipeFlavorDto = new RecipeFlavorDto();

        recipeFlavorDto.setId( recipeFlavor.getId() );
        recipeFlavorDto.setFlavor( recipeFlavor.getFlavor() );
        recipeFlavorDto.setPercentage( recipeFlavor.getPercentage() );

        return recipeFlavorDto;
    }
}

package teodor.flavor_chaser_spring_backend.mappers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.RecipeDto;
import teodor.flavor_chaser_spring_backend.entities.Rating;
import teodor.flavor_chaser_spring_backend.entities.Recipe;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T19:24:08+0300",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
*/
@Component
public class RecipeMapperImpl implements RecipeMapper {

    @Override
    public RecipeDto toDto(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDto recipeDto = new RecipeDto();

        recipeDto.setId( recipe.getId() );
        recipeDto.setName( recipe.getName() );
        recipeDto.setDescription( recipe.getDescription() );
        List<RecipeFlavor> list = recipe.getRecipeFlavors();
        if ( list != null ) {
            recipeDto.setRecipeFlavors( new ArrayList<RecipeFlavor>( list ) );
        }
        else {
            recipeDto.setRecipeFlavors( null );
        }
        List<Rating> list1 = recipe.getRatings();
        if ( list1 != null ) {
            recipeDto.setRatings( new ArrayList<Rating>( list1 ) );
        }
        else {
            recipeDto.setRatings( null );
        }

        return recipeDto;
    }
}

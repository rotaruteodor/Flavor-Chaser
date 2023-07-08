package teodor.flavor_chaser_spring_backend.mappers;

import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.IngredientInStashDto;
import teodor.flavor_chaser_spring_backend.entities.IngredientInStash;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T19:24:09+0300",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
*/
@Component
public class IngredientInStashMapperImpl implements IngredientInStashMapper {

    @Override
    public IngredientInStashDto toDto(IngredientInStash ingredientInStash) {
        if ( ingredientInStash == null ) {
            return null;
        }

        IngredientInStashDto ingredientInStashDto = new IngredientInStashDto();

        ingredientInStashDto.setId( ingredientInStash.getId() );
        ingredientInStashDto.setType( ingredientInStash.getType() );
        ingredientInStashDto.setFlavor( ingredientInStash.getFlavor() );
        ingredientInStashDto.setCurrentQuantityInMl( ingredientInStash.getCurrentQuantityInMl() );
        ingredientInStashDto.setPurchaseDate( ingredientInStash.getPurchaseDate() );
        ingredientInStashDto.setPrice( ingredientInStash.getPrice() );

        return ingredientInStashDto;
    }
}

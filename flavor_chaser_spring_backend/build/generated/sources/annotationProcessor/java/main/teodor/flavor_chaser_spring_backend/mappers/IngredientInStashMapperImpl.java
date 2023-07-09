package teodor.flavor_chaser_spring_backend.mappers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.CompanyDto;
import teodor.flavor_chaser_spring_backend.dtos.FlavorCategoryDto;
import teodor.flavor_chaser_spring_backend.dtos.FlavorDto;
import teodor.flavor_chaser_spring_backend.dtos.FlavorWarningDto;
import teodor.flavor_chaser_spring_backend.dtos.IngredientInStashDto;
import teodor.flavor_chaser_spring_backend.dtos.RatingDto;
import teodor.flavor_chaser_spring_backend.entities.Company;
import teodor.flavor_chaser_spring_backend.entities.Flavor;
import teodor.flavor_chaser_spring_backend.entities.FlavorCategory;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;
import teodor.flavor_chaser_spring_backend.entities.IngredientInStash;
import teodor.flavor_chaser_spring_backend.entities.Rating;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-09T11:36:59+0300",
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
        ingredientInStashDto.setFlavor( flavorToFlavorDto( ingredientInStash.getFlavor() ) );
        ingredientInStashDto.setCurrentQuantityInMl( ingredientInStash.getCurrentQuantityInMl() );
        ingredientInStashDto.setPurchaseDate( ingredientInStash.getPurchaseDate() );
        ingredientInStashDto.setPrice( ingredientInStash.getPrice() );

        return ingredientInStashDto;
    }

    protected CompanyDto companyToCompanyDto(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyDto companyDto = new CompanyDto();

        companyDto.setId( company.getId() );
        companyDto.setName( company.getName() );
        companyDto.setWebsite( company.getWebsite() );

        return companyDto;
    }

    protected FlavorCategoryDto flavorCategoryToFlavorCategoryDto(FlavorCategory flavorCategory) {
        if ( flavorCategory == null ) {
            return null;
        }

        FlavorCategoryDto flavorCategoryDto = new FlavorCategoryDto();

        flavorCategoryDto.setId( flavorCategory.getId() );
        flavorCategoryDto.setName( flavorCategory.getName() );

        return flavorCategoryDto;
    }

    protected FlavorWarningDto flavorWarningToFlavorWarningDto(FlavorWarning flavorWarning) {
        if ( flavorWarning == null ) {
            return null;
        }

        FlavorWarningDto flavorWarningDto = new FlavorWarningDto();

        flavorWarningDto.setId( flavorWarning.getId() );
        flavorWarningDto.setDescription( flavorWarning.getDescription() );

        return flavorWarningDto;
    }

    protected RatingDto ratingToRatingDto(Rating rating) {
        if ( rating == null ) {
            return null;
        }

        RatingDto ratingDto = new RatingDto();

        ratingDto.setId( rating.getId() );
        ratingDto.setScore( rating.getScore() );

        return ratingDto;
    }

    protected List<RatingDto> ratingListToRatingDtoList(List<Rating> list) {
        if ( list == null ) {
            return null;
        }

        List<RatingDto> list1 = new ArrayList<RatingDto>( list.size() );
        for ( Rating rating : list ) {
            list1.add( ratingToRatingDto( rating ) );
        }

        return list1;
    }

    protected FlavorDto flavorToFlavorDto(Flavor flavor) {
        if ( flavor == null ) {
            return null;
        }

        FlavorDto flavorDto = new FlavorDto();

        flavorDto.setId( flavor.getId() );
        flavorDto.setName( flavor.getName() );
        flavorDto.setDescription( flavor.getDescription() );
        flavorDto.setCompany( companyToCompanyDto( flavor.getCompany() ) );
        flavorDto.setCategory( flavorCategoryToFlavorCategoryDto( flavor.getCategory() ) );
        flavorDto.setWarning( flavorWarningToFlavorWarningDto( flavor.getWarning() ) );
        flavorDto.setRatings( ratingListToRatingDtoList( flavor.getRatings() ) );

        return flavorDto;
    }
}

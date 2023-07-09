package teodor.flavor_chaser_spring_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teodor.flavor_chaser_spring_backend.entities.Rating;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.entities.User;
import teodor.flavor_chaser_spring_backend.entities.enums.UserRecipeRelation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDto {
    private Long id;
    private String name;
    private String description;
    private List<RecipeFlavorDto> recipeFlavors;
    private List<RatingDto> ratings;
}

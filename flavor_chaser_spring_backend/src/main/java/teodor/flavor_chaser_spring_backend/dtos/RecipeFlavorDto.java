package teodor.flavor_chaser_spring_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teodor.flavor_chaser_spring_backend.entities.Flavor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeFlavorDto {
    private Long id;
    private FlavorDto flavor;
    private Double percentage;
}

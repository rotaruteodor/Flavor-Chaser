package teodor.flavor_chaser_spring_backend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teodor.flavor_chaser_spring_backend.entities.Flavor;
import teodor.flavor_chaser_spring_backend.entities.enums.MainIngredientType;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientInStashDto {

    private Long id;
    private MainIngredientType type;
    private FlavorDto flavor;
    private Double currentQuantityInMl;
    private LocalDate purchaseDate;
    private BigDecimal price;

}

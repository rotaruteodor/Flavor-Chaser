package teodor.flavor_chaser_android_app.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teodor.flavor_chaser_android_app.enums.MainIngredientType;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientInStash {

    private Long id;
    private MainIngredientType type;
    private Flavor flavor;
    private Double currentQuantityInMl;
    private LocalDate purchaseDate;
    private BigDecimal price;

}

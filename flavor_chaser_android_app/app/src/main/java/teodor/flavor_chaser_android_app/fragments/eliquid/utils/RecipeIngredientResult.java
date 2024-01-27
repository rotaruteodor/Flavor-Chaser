package teodor.flavor_chaser_android_app.fragments.eliquid.utils;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeIngredientResult {
    private String ingredientName;
    private Double milliliters;
    private Double grams;
    private Double percentageOfTotal;
    private BigDecimal cost;
}

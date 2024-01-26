package teodor.flavor_chaser_android_app.fragments.utils;

import lombok.Data;
import lombok.NonNull;

@Data
public class RecipeIngredientResult {
    @NonNull
    private String ingredientName;
    @NonNull
    private Double milliliters;
    @NonNull
    private Double grams;
    @NonNull
    private Double percentageOfTotal;
    @NonNull
    private Double cost;
}

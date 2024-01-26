package teodor.flavor_chaser_android_app.fragments.utils;

import java.util.ArrayList;

import lombok.Data;
import lombok.NonNull;

@Data
public class RecipeInputsBundle {
    @NonNull
    private BaseInputsBundle base;
    @NonNull
    private NicotineInputsBundle nicotine;
    @NonNull
    private ArrayList<FlavorInputsBundle> flavors;
    @NonNull
    private Double finalAmount;
}

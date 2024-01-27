package teodor.flavor_chaser_android_app.fragments.eliquid.utils;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeInputsBundle {
    private BaseInputsBundle base;
    private NicotineInputsBundle nicotine;
    private ArrayList<FlavorInputsBundle> flavors;
    private Double finalAmount;
}

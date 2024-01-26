package teodor.flavor_chaser_android_app.fragments.utils;

import lombok.Data;
import lombok.NonNull;

@Data
public class FlavorInputsBundle {
    @NonNull
    private String description;
    @NonNull
    private Double percentage;
    @NonNull
    private Double pricePerMl;
}

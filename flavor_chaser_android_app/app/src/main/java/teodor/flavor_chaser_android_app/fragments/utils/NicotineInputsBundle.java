package teodor.flavor_chaser_android_app.fragments.utils;

import lombok.Data;
import lombok.NonNull;

@Data
public class NicotineInputsBundle {
    @NonNull
    private Double vgPercentage;
    @NonNull
    private Double pgPercentage;
    @NonNull
    private Double nicshotStrengthInMg;
    @NonNull
    private String description;
    @NonNull
    private Double finalNicotineStrength;
}

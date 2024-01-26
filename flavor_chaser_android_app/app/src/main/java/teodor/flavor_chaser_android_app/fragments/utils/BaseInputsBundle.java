package teodor.flavor_chaser_android_app.fragments.utils;

import lombok.Data;
import lombok.NonNull;

@Data
public class BaseInputsBundle {
    @NonNull
    private Double vgPercentage;
    @NonNull
    private Double pgPercentage;
    @NonNull
    private String description;
    @NonNull
    private Boolean withIndividualPgVg;
    @NonNull
    private String pgDescription;
    @NonNull
    private String vgDescription;
}

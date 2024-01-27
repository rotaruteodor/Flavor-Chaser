package teodor.flavor_chaser_android_app.fragments.eliquid.utils;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseInputsBundle {
    private Double vgPercentage;
    private Double pgPercentage;
    private String baseDescription;
    private Boolean withIndividualPgVg;
    private String vgDescription;
    private String pgDescription;
    private BigDecimal pricePerMl;
}

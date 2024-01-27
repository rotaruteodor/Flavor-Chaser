package teodor.flavor_chaser_android_app.fragments.eliquid.utils;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NicotineInputsBundle {
    private Double vgPercentage;
    private Double pgPercentage;
    private Double strengthInMg;
    private String description;
    private Double finalStrengthInMg;
    private BigDecimal pricePerMl; // TODO Do I need this after improving cost?
}

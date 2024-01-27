package teodor.flavor_chaser_android_app.fragments.eliquid.utils;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlavorInputsBundle {
    private String description;
    private Double percentage;
    private BigDecimal pricePerMl;
}

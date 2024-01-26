package teodor.flavor_chaser_spring_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiquidCalculatorPreferencesDto {
    private Long id;
    private Double baseVgPercentage;
    private Double basePgPercentage;
    private String baseDescription;
    private boolean withIndividualPgVg;
    private String pgDescription;
    private String vgDescription;
    private Double nicshotVgPercentage;
    private Double nicshotPgPercentage;
    private Double finalNicotineStrength;
    private Double nicshotStrengthInMg;
    private String nicshotDescription;
    private Double totalFinalAmount;
}

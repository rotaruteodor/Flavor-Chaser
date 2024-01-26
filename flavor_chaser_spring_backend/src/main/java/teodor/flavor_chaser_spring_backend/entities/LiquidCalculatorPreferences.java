package teodor.flavor_chaser_spring_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "liquid_calculator_preferences")
public class LiquidCalculatorPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_vg_percentage")
    private Double baseVgPercentage;

    @Column(name = "base_pg_percentage")
    private Double basePgPercentage;

    @Column(name = "base_description")
    private String baseDescription;

    @Column(name = "with_individual_pg_vg")
    private boolean withIndividualPgVg;

    @Column(name = "pg_description")
    private String pgDescription;

    @Column(name = "vg_description")
    private String vgDescription;

    @Column(name = "nicshot_vg_percentage")
    private Double nicshotVgPercentage;

    @Column(name = "nicshot_pg_percentage")
    private Double nicshotPgPercentage;

    @Column(name = "final_nicotine_strength")
    private Double finalNicotineStrength;

    @Column(name = "nicshot_strength_in_mg")
    private Double nicshotStrengthInMg;

    @Column(name = "nicshot_description")
    private String nicshotDescription;

    @Column(name = "total_final_amount")
    private Double totalFinalAmount;
}

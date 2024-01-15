package teodor.flavor_chaser_spring_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teodor.flavor_chaser_spring_backend.entities.enums.MainIngredientType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ingredients_in_stash")
public class IngredientInStash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MainIngredientType type;

    @ManyToOne(targetEntity = Flavor.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "flavor_id")
    private Flavor flavor;

    @Column(name = "current_quantity_in_ml")
    private Double currentQuantityInMl;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}

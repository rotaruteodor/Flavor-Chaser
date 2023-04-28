package teodor.flavor_chaser_spring_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stash_flavors")
public class StashFlavor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Flavor.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "flavor_id")
    private Flavor flavor;

    @Column(name = "current_quantity")
    private Double currentQuantity;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "price")
    private BigDecimal price;

    //todo delete this entity
}
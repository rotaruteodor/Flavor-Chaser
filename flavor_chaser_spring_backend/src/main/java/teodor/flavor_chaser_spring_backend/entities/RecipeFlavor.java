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
@Table(name = "recipe_flavors")
public class RecipeFlavor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Flavor.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "flavor_id")
    private Flavor flavor;

    @Column(name = "percentage")
    private Double percentage;

}

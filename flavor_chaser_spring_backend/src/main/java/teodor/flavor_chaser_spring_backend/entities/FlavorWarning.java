package teodor.flavor_chaser_spring_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "flavor_categories")
public class FlavorWarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(targetEntity = Flavor.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "flavor_warning_id")
    private List<Flavor> flavors;

}
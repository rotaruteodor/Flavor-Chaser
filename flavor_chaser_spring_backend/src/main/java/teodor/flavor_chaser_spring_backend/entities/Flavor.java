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
@Table(name = "flavors")
public class Flavor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(targetEntity = Company.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(targetEntity = FlavorCategory.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "flavor_category_id")
    private FlavorCategory category;

    @ManyToOne(targetEntity = FlavorWarning.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "flavor_warning_id")
    private FlavorWarning warning;

    @OneToMany(targetEntity = Rating.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "flavor_id")
    private List<Rating> ratings;



}

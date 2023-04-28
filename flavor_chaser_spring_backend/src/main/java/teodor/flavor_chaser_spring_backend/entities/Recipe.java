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
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(targetEntity = RecipeFlavor.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private List<RecipeFlavor> flavors;

    @OneToMany(targetEntity = Rating.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private List<Rating> ratings;
}

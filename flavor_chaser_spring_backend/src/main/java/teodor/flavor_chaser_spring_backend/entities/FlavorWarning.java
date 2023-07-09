package teodor.flavor_chaser_spring_backend.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "flavor_warnings")
public class FlavorWarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

//    @OneToMany(targetEntity = Flavor.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "flavor_warning_id")
//    private List<Flavor> flavors;
    //todo 7. Use a Custom Serializer? https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion

}
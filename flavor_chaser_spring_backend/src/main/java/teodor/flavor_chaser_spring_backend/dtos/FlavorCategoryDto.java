package teodor.flavor_chaser_spring_backend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teodor.flavor_chaser_spring_backend.entities.Flavor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlavorCategoryDto {

    private Long id;
    private String name;
    private List<Flavor> flavors;
}
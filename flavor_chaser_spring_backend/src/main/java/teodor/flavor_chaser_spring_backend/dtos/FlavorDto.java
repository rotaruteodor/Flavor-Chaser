package teodor.flavor_chaser_spring_backend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teodor.flavor_chaser_spring_backend.entities.Company;
import teodor.flavor_chaser_spring_backend.entities.FlavorCategory;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;
import teodor.flavor_chaser_spring_backend.entities.Rating;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlavorDto {

    private Long id;
    private String name;
    private String description;
    private CompanyDto company;
    private FlavorCategoryDto category;
    private FlavorWarningDto warning;
    private List<RatingDto> ratings;

}

package teodor.flavor_chaser_android_app.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

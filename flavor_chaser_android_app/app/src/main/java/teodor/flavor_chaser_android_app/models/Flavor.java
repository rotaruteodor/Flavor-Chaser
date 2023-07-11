package teodor.flavor_chaser_android_app.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flavor {

    private Long id;
    private String name;
    private String description;
    private Company company;
    private FlavorCategory category;
    private FlavorWarning warning;
    private List<Rating> ratings;

}

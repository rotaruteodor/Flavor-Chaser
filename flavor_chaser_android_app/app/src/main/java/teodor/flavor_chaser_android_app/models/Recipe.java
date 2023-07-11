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
public class Recipe {
    private Long id;
    private String name;
    private String description;
    private List<RecipeFlavor> recipeFlavors;
    private List<Rating> ratings;
}

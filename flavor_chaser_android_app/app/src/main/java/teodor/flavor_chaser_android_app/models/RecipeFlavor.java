package teodor.flavor_chaser_android_app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeFlavor {
    private Long id;
    private Flavor flavor;
    private Double percentage;
}

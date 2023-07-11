package teodor.flavor_chaser_android_app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlavorWarning {

    private Long id;
    private String description;
//    private List<FlavorDto> flavors;
}

package teodor.flavor_chaser_android_app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlavorWarningDto {

    private Long id;
    private String description;
//    private List<FlavorDto> flavors;
}

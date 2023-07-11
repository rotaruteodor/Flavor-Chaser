package teodor.flavor_chaser_android_app.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private LocalDateTime creationDate;
    private List<IngredientInStash> ingredientsInStash;
}

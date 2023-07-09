package teodor.flavor_chaser_android_app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiExceptionDto {

    private String code;
    private String message;

//    private List<ValidationErrorDto> errors;
}

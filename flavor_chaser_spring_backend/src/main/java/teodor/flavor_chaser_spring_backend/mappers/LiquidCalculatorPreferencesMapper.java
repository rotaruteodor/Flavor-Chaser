package teodor.flavor_chaser_spring_backend.mappers;

import org.mapstruct.Mapper;
import teodor.flavor_chaser_spring_backend.dtos.LiquidCalculatorPreferencesDto;
import teodor.flavor_chaser_spring_backend.entities.LiquidCalculatorPreferences;

@Mapper
public interface LiquidCalculatorPreferencesMapper {

    LiquidCalculatorPreferencesDto toDto(LiquidCalculatorPreferences liquidCalculatorPreferences);
}

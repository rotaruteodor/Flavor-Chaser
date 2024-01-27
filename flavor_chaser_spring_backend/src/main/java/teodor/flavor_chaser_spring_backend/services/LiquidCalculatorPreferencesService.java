package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.LiquidCalculatorPreferencesDto;
import teodor.flavor_chaser_spring_backend.entities.LiquidCalculatorPreferences;
import teodor.flavor_chaser_spring_backend.mappers.LiquidCalculatorPreferencesMapper;
import teodor.flavor_chaser_spring_backend.repositories.LiquidCalculatorPreferencesRepository;

@Service
public class LiquidCalculatorPreferencesService {

    @Autowired
    LiquidCalculatorPreferencesRepository liquidCalculatorPreferencesRepository;

    @Autowired
    LiquidCalculatorPreferencesMapper liquidCalculatorPreferencesMapper;

    public ResponseEntity<LiquidCalculatorPreferencesDto> add(@RequestBody LiquidCalculatorPreferences preferences) {
        liquidCalculatorPreferencesRepository.save(preferences);
        return ResponseEntity.ok(liquidCalculatorPreferencesMapper.toDto(preferences));
    }
}

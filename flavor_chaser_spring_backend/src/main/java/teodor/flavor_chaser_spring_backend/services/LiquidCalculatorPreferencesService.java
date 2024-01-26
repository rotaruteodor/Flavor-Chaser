package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodor.flavor_chaser_spring_backend.mappers.LiquidCalculatorPreferencesMapper;
import teodor.flavor_chaser_spring_backend.repositories.LiquidCalculatorPreferencesRepository;

@Service
public class LiquidCalculatorPreferencesService {

    @Autowired
    LiquidCalculatorPreferencesRepository liquidCalculatorPreferencesRepository;

    @Autowired
    LiquidCalculatorPreferencesMapper liquidCalculatorPreferencesMapper;
}

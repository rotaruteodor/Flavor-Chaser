package teodor.flavor_chaser_spring_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teodor.flavor_chaser_spring_backend.services.LiquidCalculatorPreferencesService;

@RestController
@RequestMapping("/flavor-chaser-backend")
public class LiquidCalculatorPreferencesController {

    private static final String LIQUID_CALCULATOR_PREFERENCES_MAIN_URL = "/liquid_calculator_preferences";
    private static final String LIQUID_CALCULATOR_PREFERENCES_ID_URL = LIQUID_CALCULATOR_PREFERENCES_MAIN_URL + "/{id}";
    private static final String LIQUID_CALCULATOR_PREFERENCES_USER_URL = LIQUID_CALCULATOR_PREFERENCES_MAIN_URL + "/users/{userId}";

    @Autowired
    private LiquidCalculatorPreferencesService liquidCalculatorPreferencesService;
}

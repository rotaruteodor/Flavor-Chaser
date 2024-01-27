package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import teodor.flavor_chaser_spring_backend.dtos.UserDto;
import teodor.flavor_chaser_spring_backend.entities.LiquidCalculatorPreferences;
import teodor.flavor_chaser_spring_backend.entities.User;
import teodor.flavor_chaser_spring_backend.exceptions.ErrorMessage;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;
import teodor.flavor_chaser_spring_backend.mappers.UserMapper;
import teodor.flavor_chaser_spring_backend.repositories.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LiquidCalculatorPreferencesService liquidCalculatorPreferencesService;

    private static final String NO_DESCRIPTION_TAG = "No";
    public List<UserDto> getAll() {
        return usersRepository.findAll()
                .stream()
                .map(u -> userMapper.toDto(u))
                .toList();
    }

    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return usersRepository.findById(id)
                .map(user -> userMapper.toDto(user))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getDoesNotExistErrorMessage("User", id)));
    }

    public ResponseEntity<UserDto> getByEmailAddress(@RequestParam String emailAddress) {
        return usersRepository.findByEmailAddress(emailAddress)
                .map(user -> userMapper.toDto(user))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getUserEmailNotFoundErrorMessage(emailAddress)));
    }

    public ResponseEntity<UserDto> getByCredentials(@RequestParam String emailAddress,
                                                    @RequestParam String password) {
        return usersRepository.findByEmailAddressAndPassword(emailAddress, password)
                .map(user -> userMapper.toDto(user))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.getUserEmailNotFoundErrorMessage(emailAddress)));
    }

    public ResponseEntity<UserDto> add(@RequestBody User user) {
        user.setCreationDate(LocalDateTime.now());
        LiquidCalculatorPreferences preferences = LiquidCalculatorPreferences.builder()
                .baseVgPercentage(0.7)
                .basePgPercentage(0.3)
                .baseDescription(NO_DESCRIPTION_TAG)
                .withIndividualPgVg(false)
                .vgDescription(NO_DESCRIPTION_TAG)
                .pgDescription(NO_DESCRIPTION_TAG)
                .nicshotVgPercentage(0.7)
                .nicshotPgPercentage(0.3)
                .nicshotStrengthInMg(20d)
                .finalNicotineStrength(6d)
                .nicshotDescription(NO_DESCRIPTION_TAG)
                .totalFinalAmount(10d)
                .build();

        liquidCalculatorPreferencesService.add(preferences);
        user.setLiquidCalculatorPreferences(preferences);
        usersRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}

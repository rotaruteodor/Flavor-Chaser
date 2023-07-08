package teodor.flavor_chaser_spring_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import teodor.flavor_chaser_spring_backend.dtos.RecipeDto;
import teodor.flavor_chaser_spring_backend.dtos.UserDto;
import teodor.flavor_chaser_spring_backend.entities.Recipe;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;
import teodor.flavor_chaser_spring_backend.entities.User;
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

    public List<UserDto> getAll() {
        return usersRepository.findAll()
                .stream()
                .map(u -> userMapper.toDto(u))
                .toList();
    }

    public ResponseEntity<UserDto> add(@RequestBody User user) {
        user.setCreationDate(LocalDateTime.now());
        usersRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}

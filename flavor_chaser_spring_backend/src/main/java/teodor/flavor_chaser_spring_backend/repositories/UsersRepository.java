package teodor.flavor_chaser_spring_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teodor.flavor_chaser_spring_backend.dtos.UserDto;
import teodor.flavor_chaser_spring_backend.entities.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAddressAndPassword(String emailAddress, String password);
    Optional<User> findByEmailAddress(String emailAddress);
}

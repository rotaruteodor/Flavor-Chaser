package teodor.flavor_chaser_spring_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teodor.flavor_chaser_spring_backend.entities.FlavorWarning;

@Repository
public interface FlavorWarningsRepository extends JpaRepository<FlavorWarning, Long> {
}

package teodor.flavor_chaser_spring_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teodor.flavor_chaser_spring_backend.entities.RecipeFlavor;

@Repository
public interface RecipeFlavorsRepository extends JpaRepository<RecipeFlavor, Long> {
}

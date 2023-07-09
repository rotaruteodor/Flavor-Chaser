package teodor.flavor_chaser_spring_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teodor.flavor_chaser_spring_backend.dtos.FlavorDto;
import teodor.flavor_chaser_spring_backend.entities.Flavor;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlavorsRepository extends JpaRepository<Flavor, Long> {

}

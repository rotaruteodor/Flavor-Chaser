package teodor.flavor_chaser_spring_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teodor.flavor_chaser_spring_backend.entities.Flavor;

@Repository
public interface FlavorsRepository extends JpaRepository<Flavor, Long> {

//    TODO Do I need this?
//    @Query(value = "SELECT * FROM flavors f WHERE f.name=?1 AND f.company_id=?2", nativeQuery = true)
//    Optional<Flavor> findByNameAndCompany(String name, String companyId);
}

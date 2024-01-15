package teodor.flavor_chaser_spring_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teodor.flavor_chaser_spring_backend.entities.IngredientInStash;

import java.util.List;

@Repository
public interface IngredientsInStashRepository extends JpaRepository<IngredientInStash, Long> {

    @Query(value = "SELECT * FROM ingredients_in_stash i WHERE i.user_id=?1", nativeQuery = true)
    List<IngredientInStash> findAllByUserId(Long userId);
}

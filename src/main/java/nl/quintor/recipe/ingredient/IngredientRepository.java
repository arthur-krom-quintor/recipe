package nl.quintor.recipe.ingredient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}

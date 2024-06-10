package nl.quintor.recipe.ingredient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Long, Ingredient> {
}

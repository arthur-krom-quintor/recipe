package nl.quintor.recipe.recipe;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles persistence for recipe
 */
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
}

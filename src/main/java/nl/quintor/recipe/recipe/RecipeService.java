package nl.quintor.recipe.recipe;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles business logic for recipe
 */
@Service
public class RecipeService {
    private final EntityManager entityManager;
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(EntityManager entityManager, RecipeRepository recipeRepository) {
        this.entityManager = entityManager;
        this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }
}

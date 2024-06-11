package nl.quintor.recipe.recipe;

import nl.quintor.recipe.exception.DuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Handles business logic for recipe
 */
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * Saves the recipe.
     * @param recipe
     * @return
     */
    public Recipe createRecipe(Recipe recipe){
        var found = recipeRepository.findByName(recipe.getName());
        if(found.isPresent()){
            throw new DuplicateException(DuplicateException.UNIQUE_RECIPE_NAME);
        }
        return recipeRepository.save(recipe);
    }

    /**
     * Finds all recipes and returns them.
     * @return
     */
    public Set<Recipe> readAllRecipes(){
        var result = new HashSet<Recipe>();
        recipeRepository.findAll().forEach(result::add);
        return result;
    }
}

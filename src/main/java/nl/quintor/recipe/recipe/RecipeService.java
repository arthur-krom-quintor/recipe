package nl.quintor.recipe.recipe;

import jakarta.validation.Valid;
import nl.quintor.recipe.exception.ResourceNotFoundException;
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
        return recipeRepository.save(recipe);
    }


    /**
     * Updates the recipe or creates a new one if it doesn't exist yet
     * @param recipe
     * @return
     */
    public Recipe updateRecipe(Recipe recipe){
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

    /**
     * Deletes the recipe (without deleting the ingredients)
     * @param id
     */
    public void deleteRecipe(Integer id){
        recipeRepository.deleteById(id);
    }

    /**
     * Finds a recipe by id
     * @param id, the id of the recipe
     * @return The recipe, or throw a ResourceNotFoundException
     */
    public Recipe readRecipeById(Integer id){
        var result = recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundException.RECIPE_NOT_FOUND));
        return result;
    }
}

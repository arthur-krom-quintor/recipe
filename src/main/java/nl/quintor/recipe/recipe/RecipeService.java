package nl.quintor.recipe.recipe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import nl.quintor.recipe.exception.ResourceNotFoundException;
import nl.quintor.recipe.ingredient.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles business logic for recipe
 */
@Service
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final EntityManager entityManager;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, EntityManager entityManager) {
        this.recipeRepository = recipeRepository;
        this.entityManager = entityManager;
    }

    /**
     * Saves the recipe.
     *
     * @param recipe
     * @return
     */
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }


    /**
     * Updates the recipe or creates a new one if it doesn't exist yet
     *
     * @param recipe
     * @return
     */
    public Recipe updateRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    /**
     * Finds all recipes and returns them.
     *
     * @return
     */
    public Set<Recipe> readAllRecipes() {
        return new HashSet<Recipe>((List<Recipe>) recipeRepository.findAll());
    }


    /**
     * Returns a set of recipes which adhere to the (optional) filters.
     * The implementation of this feature should probably be done using the Criteria API
     *
     * @param servingsFilter
     * @param vegetarianFilter
     * @param instructionFilter
     * @param includesFilter
     * @param excludesFilter
     * @return Set of recipes adhering to the filter
     */
    public Set<Recipe> readAllRecipesFilteredByStreams(Optional<Integer> servingsFilter, Optional<Boolean> vegetarianFilter,
                                                       Optional<String> instructionFilter, Optional<List<String>> includesFilter,
                                                       Optional<List<String>> excludesFilter) {
        var current = readAllRecipes();

        if (servingsFilter.isPresent()) {
            current = current
                    .stream()
                    .filter(r -> Objects.equals(r.getServings(), servingsFilter.get()))
                    .collect(Collectors.toSet());
        }

        if (vegetarianFilter.isPresent()) {
            if (vegetarianFilter.get()) {
                current = current
                        .stream()
                        .filter(r -> r.getIngredients().stream().allMatch(Ingredient::getIsVegetarian))
                        .collect(Collectors.toSet());
            } else {
                current = current
                        .stream().
                        filter(r -> r.getIngredients().stream().anyMatch(i -> !i.getIsVegetarian()))
                        .collect(Collectors.toSet());
            }
        }

        if (instructionFilter.isPresent()) {
            current = current
                    .stream()
                    .filter(r -> r.getInstructions().contains(instructionFilter.get()))
                    .collect(Collectors.toSet());
        }

        if (includesFilter.isPresent()) {
            for (var include : includesFilter.get()) {
                current = current
                        .stream()
                        .filter(r -> r.getIngredients()
                                .stream()
                                .anyMatch(i -> i.getName().equalsIgnoreCase(include)))
                        .collect(Collectors.toSet());
            }
        }

        if (excludesFilter.isPresent()){
            for (var exclude : excludesFilter.get()) {
                current = current
                        .stream()
                        .filter(r -> r.getIngredients()
                                .stream()
                                .noneMatch(i -> i.getName().equalsIgnoreCase(exclude)))
                        .collect(Collectors.toSet());
            }
        }
        return current;
    }


    /**
     * Deletes the recipe (without deleting the ingredients)
     *
     * @param id
     */
    public void deleteRecipe(Integer id) {
        recipeRepository.deleteById(id);
    }

    /**
     * Finds a recipe by id
     *
     * @param id, the id of the recipe
     * @return The recipe, or throw a ResourceNotFoundException
     */
    public Recipe readRecipeById(Integer id) {
        var result = recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundException.RECIPE_NOT_FOUND));
        return result;
    }
}

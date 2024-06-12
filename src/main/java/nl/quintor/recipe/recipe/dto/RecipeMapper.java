package nl.quintor.recipe.recipe.dto;

import nl.quintor.recipe.ingredient.Ingredient;
import nl.quintor.recipe.ingredient.IngredientService;
import nl.quintor.recipe.ingredient.dto.IngredientMapper;
import nl.quintor.recipe.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {

    private final IngredientMapper ingredientMapper;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeMapper(IngredientMapper ingredientMapper, IngredientService ingredientService) {
        this.ingredientMapper = ingredientMapper;
        this.ingredientService = ingredientService;
    }

    /**
     * Maps a RecipeCreateRequest to a Recipe
     * @param recipeCreateRequest
     * @return the Recipe
     */
    public Recipe recipeCreateRequestToRecipe(RecipeCreateRequest recipeCreateRequest){
        var recipe = new Recipe();
        recipe.setName(recipeCreateRequest.getName());
        recipe.setServings(recipeCreateRequest.getServings());
        recipe.setInstructions(recipeCreateRequest.getInstructions());

        var ingredients = recipeCreateRequest.getIngredients().stream().map(ingredientService::readIngredientById).collect(Collectors.toSet());
        recipe.setIngredients(ingredients);

        return recipe;
    }

    /**
     * Maps a recipe to a RecipeResponse.
     * Whether a recipe is vegetarian is determined based on whether all ingredients are vegetarian
     * @param recipe
     * @return the RecipeResponse
     */
    public RecipeResponse recipeToRecipeResponse(Recipe recipe){
        var recipeResponse = new RecipeResponse();
        recipeResponse.setId(recipe.getId());
        recipeResponse.setName(recipe.getName());
        recipeResponse.setServings(recipe.getServings());
        recipeResponse.setInstructions(recipe.getInstructions());

        var ingredients = ingredientMapper.ingredientSetToIngredientResponseSet(recipe.getIngredients());
        recipeResponse.setIngredients(ingredients);

        var isVegetarian = recipe.getIngredients().stream().allMatch(Ingredient::getIsVegetarian);
        recipeResponse.setIsVegetarian(isVegetarian);

        return recipeResponse;
    }

    public Set<RecipeResponse> recipeSetToRecipeResponseSet(Set<Recipe> recipes){
        return recipes.stream().map(this::recipeToRecipeResponse).collect(Collectors.toSet());
    }
}

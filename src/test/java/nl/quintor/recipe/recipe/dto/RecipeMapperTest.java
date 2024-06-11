package nl.quintor.recipe.recipe.dto;

import nl.quintor.recipe.ingredient.Ingredient;
import nl.quintor.recipe.ingredient.IngredientService;
import nl.quintor.recipe.ingredient.dto.IngredientMapper;
import nl.quintor.recipe.recipe.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeMapperTest {

    @Mock
    IngredientService ingredientService;

    @Mock
    IngredientMapper ingredientMapper;

    @InjectMocks
    RecipeMapper recipeMapper;

    @Test
    void recipeCreateRequestToRecipe() {
        var name = "Special recipe";
        var servings = 1;
        var instructions = "Magic";

        var beef = new Ingredient(1, "beef", false, null);
        var rice = new Ingredient(2, "rice", true, null);
        var ingredients = Set.of(beef, rice);
        var ingredientIds = ingredients.stream().map(i -> i.getId()).collect(Collectors.toSet());

        var recipeCreateRequest = new RecipeCreateRequest(name, servings, instructions, ingredientIds);
        var expected = new Recipe(null ,name, servings, instructions, ingredients);

        when(ingredientService.readIngredientById(beef.getId())).thenReturn(beef);
        when(ingredientService.readIngredientById(rice.getId())).thenReturn(rice);

        var result = recipeMapper.recipeCreateRequestToRecipe(recipeCreateRequest);
        assertEquals(expected, result);
    }
}
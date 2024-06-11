package nl.quintor.recipe.ingredient.dto;

import nl.quintor.recipe.ingredient.Ingredient;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Used to map between an ingredient and its related DTOs.
 */
@Component
public class IngredientMapper {

    public IngredientResponse ingredientToIngredientResponse(Ingredient ingredient){
        var result = new IngredientResponse();
        result.setId(ingredient.getId());
        result.setName(ingredient.getName());
        result.setIsVegetarian(ingredient.getIsVegetarian());
        return result;
    }

    public Set<IngredientResponse> ingredientSetToIngredientResponseSet(Set<Ingredient> ingredients){
        return ingredients.stream().map(this::ingredientToIngredientResponse).collect(Collectors.toSet());
    }
}

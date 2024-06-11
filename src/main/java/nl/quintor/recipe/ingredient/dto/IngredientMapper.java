package nl.quintor.recipe.ingredient.dto;

import nl.quintor.recipe.ingredient.Ingredient;
import org.springframework.stereotype.Component;

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
}

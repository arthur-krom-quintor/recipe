package nl.quintor.recipe.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.quintor.recipe.ingredient.dto.IngredientResponse;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeResponse {
    private Integer id;
    private String name;
    private Integer servings;
    private String instructions;
    private Boolean isVegetarian;
    private Set<IngredientResponse> ingredients;
}

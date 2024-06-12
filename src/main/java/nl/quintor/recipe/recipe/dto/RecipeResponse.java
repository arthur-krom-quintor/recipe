package nl.quintor.recipe.recipe.dto;

import lombok.*;
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

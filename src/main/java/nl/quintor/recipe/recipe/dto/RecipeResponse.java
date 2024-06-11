package nl.quintor.recipe.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.quintor.recipe.ingredient.dto.IngredientResponse;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeResponse {
    private Integer id;
    private String name;
    private Integer servings;
    private String instructions;
    private Boolean isVegetarian;
    private Set<IngredientResponse> ingredients;
}

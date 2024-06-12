package nl.quintor.recipe.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API representation of an ingredient.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IngredientResponse {
    private Integer id;
    private String name;
    private Boolean isVegetarian;
}

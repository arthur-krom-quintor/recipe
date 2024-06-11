package nl.quintor.recipe.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * API representation of an ingredient.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IngredientResponse {
    private Integer id;
    private String name;
    private Boolean isVegetarian;
}

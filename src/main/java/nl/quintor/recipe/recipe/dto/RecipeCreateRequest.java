package nl.quintor.recipe.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeCreateRequest {
    private String name;
    private Integer servings;
    private String instructions;

    // Contains the id's of the ingredients
    private Set<Integer> ingredients;
}

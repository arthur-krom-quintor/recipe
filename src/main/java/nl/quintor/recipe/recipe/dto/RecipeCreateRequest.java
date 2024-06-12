package nl.quintor.recipe.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeCreateRequest {
    @NotBlank(message = "A name is required and cant consist solely of whitespace.")
    private String name;

    @NotNull(message = "The amount of servings is required.")
    @Positive(message = "Servings must be a positive integer.")
    private Integer servings;

    @NotBlank(message = "Instructions are required.")
    private String instructions;

    @NotEmpty(message = "A recipe must consist of at least 1 ingredient.")
    // Contains the id's of the ingredients
    private Set<Integer> ingredients;
}

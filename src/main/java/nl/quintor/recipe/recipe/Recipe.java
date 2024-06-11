package nl.quintor.recipe.recipe;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;
import nl.quintor.recipe.ingredient.Ingredient;

import java.util.Set;

/**
 * Represents a recipe
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer servings;
    private String instructions;

    @ManyToMany
    @JoinTable(name = "recipe_contains_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "ingredient_id"}))
    private Set<Ingredient> ingredients;
}

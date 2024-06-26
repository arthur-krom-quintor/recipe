package nl.quintor.recipe.recipe;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "recipe_contains_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "ingredient_id"}))
    private Set<Ingredient> ingredients;
}

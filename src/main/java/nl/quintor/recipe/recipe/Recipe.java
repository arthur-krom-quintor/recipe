package nl.quintor.recipe.recipe;

import jakarta.persistence.*;
import nl.quintor.recipe.ingredient.Ingredient;

import java.util.Set;

/**
 * Represents a recipe
 */
@Entity
public class Recipe {
    @Id
    private Long id;
    private Integer servings;
    private String instructions;

    @ManyToMany
    @JoinTable(name = "recipe_contains_ingredient", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients;
}

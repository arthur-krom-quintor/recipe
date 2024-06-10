package nl.quintor.recipe.recipe;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.quintor.recipe.ingredient.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Represents a recipe
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer servings;
    private String instructions;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "recipe_contains_ingredient", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients;
}

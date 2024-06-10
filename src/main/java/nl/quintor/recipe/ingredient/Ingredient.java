package nl.quintor.recipe.ingredient;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import nl.quintor.recipe.recipe.Recipe;

import java.util.Set;

/**
 * Represents an ingredient
 */
@Entity
public class Ingredient {
    @Id
    private Integer id;
    private String name;
    private Boolean isVegetarian;
    @ManyToMany(mappedBy = "ingredients")
    private Set<Recipe> recipes;
}

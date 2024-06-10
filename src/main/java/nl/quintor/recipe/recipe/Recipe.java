package nl.quintor.recipe.recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Represents a recipe
 */
@Entity
public class Recipe {
    @Id
    private Long id;

}

package nl.quintor.recipe.ingredient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.quintor.recipe.recipe.Recipe;

import java.util.Set;

/**
 * Represents an ingredient.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TINYINT")
    private Boolean isVegetarian;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Recipe> recipes;
}

package nl.quintor.recipe.ingredient;

import jakarta.persistence.*;
import lombok.*;
import nl.quintor.recipe.recipe.Recipe;

import java.util.Set;

/**
 * Represents an ingredient.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(columnDefinition = "TINYINT")
    private Boolean isVegetarian;

//    @EqualsAndHashCode.Exclude
//    @ManyToMany(mappedBy = "ingredients")
//    private Set<Recipe> recipes;
}

package nl.quintor.recipe.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * This class is used to load some initial data regarding ingredients into the database.
 */
@Component
@Order(1)
public class IngredientDataLoader implements CommandLineRunner {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientDataLoader(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        var ingredients = new HashSet<Ingredient>();
        ingredients.add(new Ingredient(null, "Milk", true ));
        ingredients.add(new Ingredient(null, "Nuts", true ));
        ingredients.add(new Ingredient(null, "Beef", false ));
        ingredients.add(new Ingredient(null, "Pork", false ));
        ingredients.add(new Ingredient(null, "Chicken", false ));
        ingredients.add(new Ingredient(null, "Fish", false ));
        ingredients.add(new Ingredient(null, "Flour", true ));
        ingredients.add(new Ingredient(null, "Garlic", true ));
        ingredients.add(new Ingredient(null, "Tomato", true ));
        ingredients.add(new Ingredient(null, "Salt", true ));
        ingredients.add(new Ingredient(null, "Pepper", true ));
        ingredients.add(new Ingredient(null, "Rice", true ));
        ingredients.add(new Ingredient(null, "Egg", true ));
        ingredients.add(new Ingredient(null, "Broccoli", true ));
        ingredients.add(new Ingredient(null, "Cauliflower", true ));
        ingredients.add(new Ingredient(null, "Yeast", true ));
        ingredients.add(new Ingredient(null, "Sugar", true ));
        ingredients.add(new Ingredient(null, "Baking powder", true ));
        ingredients.add(new Ingredient(null, "Ham", false ));
        ingredients.add(new Ingredient(null, "Cheese", true ));
        ingredients.add(new Ingredient(null, "Butter", true ));

        ingredients.forEach(ingredientService::createIngredient);
    }
}

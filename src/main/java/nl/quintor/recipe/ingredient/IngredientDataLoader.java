package nl.quintor.recipe.ingredient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Slf4j
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
        ingredients.add(new Ingredient(null, "Milk", true, null ));
        ingredients.add(new Ingredient(null, "Nuts", true, null ));
        ingredients.add(new Ingredient(null, "Beef", false, null ));
        ingredients.add(new Ingredient(null, "Pork", false, null ));
        ingredients.add(new Ingredient(null, "Chicken", false, null ));
        ingredients.add(new Ingredient(null, "Fish", false, null ));
        ingredients.add(new Ingredient(null, "Flour", true, null ));
        ingredients.add(new Ingredient(null, "Garlic", true, null ));
        ingredients.add(new Ingredient(null, "Tomato", true, null ));
        ingredients.add(new Ingredient(null, "Salt", true, null ));
        ingredients.add(new Ingredient(null, "Pepper", true, null ));
        ingredients.add(new Ingredient(null, "Rice", true, null ));
        ingredients.add(new Ingredient(null, "Egg", true, null ));
        ingredients.add(new Ingredient(null, "Broccoli", true, null ));
        ingredients.add(new Ingredient(null, "Cauliflower", true, null ));
        ingredients.add(new Ingredient(null, "Yeast", true, null ));
        ingredients.add(new Ingredient(null, "Sugar", true, null ));
        ingredients.add(new Ingredient(null, "Baking powder", true, null ));
        ingredients.add(new Ingredient(null, "Ham", false, null ));
        ingredients.add(new Ingredient(null, "Cheese", true, null ));
        ingredients.add(new Ingredient(null, "Butter", true, null ));

        ingredients.forEach(i ->{
            ingredientService.createIngredient(i);
        });
    }
}

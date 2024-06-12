package nl.quintor.recipe.recipe;

import nl.quintor.recipe.ingredient.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Loads initial recipe data
 */
@Component
@Order(2)
public class RecipeDataLoader implements CommandLineRunner {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeDataLoader(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        var ingredients = ingredientService.readAllIngredients();
        var eggs = ingredients.stream().filter(i -> i.getName().equals("Egg")).findFirst().orElseThrow(() -> new RuntimeException("Couldnt find eggs"));
        var ham = ingredients.stream().filter(i -> i.getName().equals("Ham")).findFirst().orElseThrow(() -> new RuntimeException("Couldnt find hams"));
        var cheese = ingredients.stream().filter(i -> i.getName().equals("Cheese")).findFirst().orElseThrow(() -> new RuntimeException("Couldnt find cheese"));
        var flour = ingredients.stream().filter(i -> i.getName().equals("Flour")).findFirst().orElseThrow(() -> new RuntimeException("Couldnt find flour"));
        var milk = ingredients.stream().filter(i -> i.getName().equals("Milk")).findFirst().orElseThrow(() -> new RuntimeException("Couldnt find milk"));
        var pancake = new Recipe(null, "Pancake", 6, ".1 add flour, eggs, and milk in a bowl. .2 pour mixture in pan", Set.of(eggs, milk));
        var friedEgg = new Recipe(null, "Fried egg with ham and cheese", 1, ".1 put butter in pan. 2. let it melt. 3. Fry the eggs. .4 add cheese and ham ", Set.of(ham, cheese,eggs));
        var boiledEgg = new Recipe(null, "Boiled egg", 1, "1. let water boil. 2. add egg. 3. wait 8 minutes", Set.of(eggs));
        recipeService.createRecipe(pancake);
        recipeService.createRecipe(friedEgg);
        recipeService.createRecipe(boiledEgg);
    }
}

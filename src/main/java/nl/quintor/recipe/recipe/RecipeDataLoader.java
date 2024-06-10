package nl.quintor.recipe.recipe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * Loads initial recipe data
 */
@Slf4j
@Component
public class RecipeDataLoader implements CommandLineRunner {
    private final RecipeService recipeService;

    @Autowired
    public RecipeDataLoader(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public void run(String... args) throws Exception {
    }
}

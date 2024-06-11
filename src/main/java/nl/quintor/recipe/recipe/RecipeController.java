package nl.quintor.recipe.recipe;

import nl.quintor.recipe.recipe.dto.RecipeMapper;
import nl.quintor.recipe.recipe.dto.RecipeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles HTTP requests for recipe
 */
@RequestMapping("/recipe")
@RestController
public class RecipeController {

    private final RecipeMapper recipeMapper;
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeMapper recipeMapper, RecipeService recipeService) {
        this.recipeMapper = recipeMapper;
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<Set<RecipeResponse>> readAllRecipes(){
        var recipeSet = recipeService.readAllRecipes();
        var response = recipeSet.stream().map(recipeMapper::recipeToRecipeResponse).collect(Collectors.toSet());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

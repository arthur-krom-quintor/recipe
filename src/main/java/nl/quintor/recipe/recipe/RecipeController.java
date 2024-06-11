package nl.quintor.recipe.recipe;

import jakarta.validation.Valid;
import nl.quintor.recipe.recipe.dto.RecipeCreateRequest;
import nl.quintor.recipe.recipe.dto.RecipeMapper;
import nl.quintor.recipe.recipe.dto.RecipeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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


    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody @Valid RecipeCreateRequest recipeCreateRequest){
        var recipeToCreate = recipeMapper.recipeCreateRequestToRecipe(recipeCreateRequest);
        var savedRecipe = recipeService.createRecipe(recipeToCreate);
        var responseRecipe = recipeMapper.recipeToRecipeResponse(savedRecipe);
        URI uri = UriComponentsBuilder.fromUriString("recipe/" + savedRecipe.getId()).build().toUri();
        return ResponseEntity.created(uri).body(responseRecipe);
    }
}

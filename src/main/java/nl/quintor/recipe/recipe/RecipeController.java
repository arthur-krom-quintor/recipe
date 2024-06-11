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

    /**
     * Fetches all available recipes
     * @return a set containing all available recipes
     */
    @GetMapping
    public ResponseEntity<Set<RecipeResponse>> readAllRecipes(){
        var recipeSet = recipeService.readAllRecipes();
        var response = recipeSet.stream().map(recipeMapper::recipeToRecipeResponse).collect(Collectors.toSet());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Returns a RecipeResponse, if the recipe couldn't be found the RecipeService will throw a ResourceNotFoundException
     * @param id the id associated with the recipe
     * @return The recipe associated with the provided id
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> readRecipeById(@PathVariable Integer id){
        var foundRecipe = recipeService.readRecipeById(id);
        var response = recipeMapper.recipeToRecipeResponse(foundRecipe);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Creates a recipe through RecipeService
     * @param recipeCreateRequest The body to which the request body must adhere
     * @return RecipeReponse the newly created recipe, including the uri
     */
    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody @Valid RecipeCreateRequest recipeCreateRequest){
        var recipeToCreate = recipeMapper.recipeCreateRequestToRecipe(recipeCreateRequest);
        var savedRecipe = recipeService.createRecipe(recipeToCreate);
        var responseRecipe = recipeMapper.recipeToRecipeResponse(savedRecipe);
        URI uri = UriComponentsBuilder.fromUriString("recipe/" + savedRecipe.getId()).build().toUri();
        return ResponseEntity.created(uri).body(responseRecipe);
    }

    /**
     * Updates a recipe associated with the provided id. If the recipe couldn't be found, a new one is created.
     * @param id the id of the entity we wish to update
     * @param recipeCreateRequest The PUT request requires the same body as the POST request
     * @return ResponseEntity containing the RecipeResponse of the updated or newly created Recipe
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable Integer id, @RequestBody @Valid RecipeCreateRequest recipeCreateRequest){
        var recipeToUpdate = recipeMapper.recipeCreateRequestToRecipe(recipeCreateRequest);
        recipeToUpdate.setId(id);
        var updatedRecipe = recipeService.updateRecipe(recipeToUpdate);
        var response = recipeMapper.recipeToRecipeResponse(updatedRecipe);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes the recipe, no exception will be thrown if the recipe didn't exist in the first place
     * @param id The id associated with the to be deleted recipe
     * @return ResponseEntity containing the status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Integer id){
       recipeService.deleteRecipe(id);
       return new ResponseEntity<>(HttpStatus.OK);
    }
}

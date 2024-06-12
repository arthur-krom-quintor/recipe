package nl.quintor.recipe.recipe;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nl.quintor.recipe.recipe.dto.RecipeCreateRequest;
import nl.quintor.recipe.recipe.dto.RecipeMapper;
import nl.quintor.recipe.recipe.dto.RecipeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Handles HTTP requests for recipe
 */
@RequestMapping("/recipe")
@RestController
@Slf4j
public class RecipeController {

    private final RecipeMapper recipeMapper;
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeMapper recipeMapper, RecipeService recipeService) {
        this.recipeMapper = recipeMapper;
        this.recipeService = recipeService;
    }


    /**
     * Fetches all available recipes based on optional filters
     * @return a ResponseEntity containing a set containing all available recipes
     */
    @Operation(summary = "Retrieves all recipes that adhere to the filters",
            description = "Retrieves all recipes that adhere to the filters. All filters are optional and can be combined.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This endpoint returns OK (200) whether the set of recipes contains recipes or not.")
    })
    @GetMapping
    public ResponseEntity<Set<RecipeResponse>> readAllRecipes(@RequestParam(required = false) Optional<Integer> servingsFilter,
                                                                      @RequestParam(required = false) Optional<Boolean> vegetarianFilter,
                                                                      @RequestParam(required = false) Optional<String> instructionFilter,
                                                                      @RequestParam(required = false) Optional<List<String>> includesFilter,
                                                                      @RequestParam(required = false) Optional<List<String>> excludesFilter){
        var recipeSet = recipeService.readAllRecipesFilteredByStreams(servingsFilter, vegetarianFilter, instructionFilter, includesFilter, excludesFilter);
        var recipeResponseSet = recipeMapper.recipeSetToRecipeResponseSet(recipeSet);
        return new ResponseEntity<>(recipeResponseSet, HttpStatus.OK);
    }

    /**
     * Returns a RecipeResponse, if the recipe couldn't be found the RecipeService will throw a ResourceNotFoundException
     * @param id the id associated with the recipe
     * @return The recipe associated with the provided id
     */
    @Operation(summary = "Retrieves a recipe by their id.")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The recipe has been found and is returned in the body."),
            @ApiResponse(responseCode = "404", description = "The recipe could not be found.")
    })
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
    @Operation(summary = "Creates a recipe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The recipe has been created succesfully."),
            @ApiResponse(responseCode = "404", description = "One or more ingredients used in this recipe couldn't be found.")
    })
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
    @Operation(summary = "Updates a recipe.",
            description = "Updates a recipe. When a recipe can't be found, a new one is created." +
                    " Note that the id of the newly created one might differ from the one provided in the path.")
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
    @Operation(summary = "Deletes a recipe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "This endpoint returns NO CONTENT (204) whether the recipe was deleted" +
                    " or didn't exist in the first place.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Integer id){
       recipeService.deleteRecipe(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

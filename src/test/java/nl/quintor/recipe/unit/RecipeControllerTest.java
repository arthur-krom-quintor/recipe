package nl.quintor.recipe.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.quintor.recipe.exception.ErrorResponse;
import nl.quintor.recipe.ingredient.Ingredient;
import nl.quintor.recipe.ingredient.IngredientService;
import nl.quintor.recipe.ingredient.dto.IngredientMapper;
import nl.quintor.recipe.recipe.Recipe;
import nl.quintor.recipe.recipe.RecipeController;
import nl.quintor.recipe.recipe.RecipeService;
import nl.quintor.recipe.recipe.dto.RecipeCreateRequest;
import nl.quintor.recipe.recipe.dto.RecipeMapper;
import nl.quintor.recipe.recipe.dto.RecipeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(IngredientMapper.class)
@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    IngredientMapper ingredientMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RecipeService recipeService;

    @MockBean
    IngredientService ingredientService;

    @MockBean
    RecipeMapper recipeMapper;

    // Chose List instead of Set so that I know which specific element I am referring to in tests
    private List<Recipe> recipes;
    private List<RecipeResponse> recipeResponses;

    @BeforeEach
    void setUp() {
        var eggs = new Ingredient(1, "eggs", true);
        var milk = new Ingredient(2, "milk", true);
        var flour = new Ingredient(3, "flour", true);
        var beef = new Ingredient(4, "beef", false);
        var ham = new Ingredient(5, "ham", false);
        var rice = new Ingredient(6, "rice", true);
        var broccoli = new Ingredient(7, "broccoli", true);

        var special1Ingredients = Set.of(eggs, ham);
        var special2Ingredients = Set.of(rice, broccoli, beef);
        var special1 = new Recipe(1, "Special 1", 3, "1. fry egg. 2. add ham", special1Ingredients);
        var special2 = new Recipe(2, "Special 2", 4, "1. Cook rice. 2. Fry beef. 3. cook broccoli. 4. add on plate. 5. add sauce", Set.of(rice, broccoli, beef));
        recipes = List.of(special1, special2);

        var special1ResponseIngredients = ingredientMapper.ingredientSetToIngredientResponseSet(special1Ingredients);
        var special1Response = new RecipeResponse(special1.getId(), special1.getName(), special1.getServings(), special1.getInstructions(), false, special1ResponseIngredients);

        var special2ResponseIngredients = ingredientMapper.ingredientSetToIngredientResponseSet(special2Ingredients);
        var special2Response = new RecipeResponse(special2.getId(), special2.getName(), special2.getServings(), special2.getInstructions(), false, special2ResponseIngredients);

        recipeResponses = List.of(special1Response, special2Response);
    }

    @Test
    void readAllRecipes() throws Exception {
        var recipeHashSetToReturn = new HashSet<>(recipes);
        var recipeResponseHashSetToReturn = new HashSet<>(recipeResponses);

        when(recipeService.readAllRecipes()).thenReturn(recipeHashSetToReturn);
        when(recipeMapper.recipeSetToRecipeResponseSet(recipeHashSetToReturn)).thenReturn(recipeResponseHashSetToReturn);

        var expectedResponse = recipeResponses;
        String expectedResponseString = objectMapper.writeValueAsString(expectedResponse);
        mockMvc.perform(get("/recipe"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponseString))
                .andReturn();
    }

    @Test
    void createValidRecipeShouldReturnRecipeWithStatusIsCreated() throws Exception {
        var recipe = recipes.getFirst();
        var recipeWithoutId = new Recipe(null, recipe.getName(), recipe.getServings(), recipe.getInstructions(), recipe.getIngredients());
        var recipeCreateRequest = new RecipeCreateRequest(recipe.getName(), recipe.getServings(), recipe.getInstructions(),
                recipe.getIngredients().stream().map(i->i.getId()).collect(Collectors.toSet()));
        var expectedIngredientsReponse = ingredientMapper.ingredientSetToIngredientResponseSet(recipe.getIngredients());
        var recipeResponse = new RecipeResponse(recipe.getId(), recipe.getName(), recipe.getServings(), recipe.getInstructions(), true, expectedIngredientsReponse);


        when(recipeMapper.recipeCreateRequestToRecipe(recipeCreateRequest)).thenReturn(recipeWithoutId);
        when(recipeService.createRecipe(recipeWithoutId)).thenReturn(recipe);
        when(recipeMapper.recipeToRecipeResponse(recipe)).thenReturn(recipeResponse);

        var recipeCreateRequestString = objectMapper.writeValueAsString(recipeCreateRequest);

        mockMvc.perform(post("/recipe").content(recipeCreateRequestString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(recipeResponse)))
                .andReturn();
    }

    @Test
    void createRecipeWithoutNameShouldReturnError() throws Exception {
        var recipeCreateRequest = new RecipeCreateRequest();
        recipeCreateRequest.setIngredients(Set.of(1));
        recipeCreateRequest.setInstructions("Bla");
        recipeCreateRequest.setServings(1);

        var recipeCreateRequestString = objectMapper.writeValueAsString(recipeCreateRequest);
        var path = "/recipe";
        var expectedErrorResponse = new ErrorResponse(path, "A name is required and cant consist solely of whitespace.");

        mockMvc.perform(post(path).content(recipeCreateRequestString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedErrorResponse)))
                .andReturn();
    }
}
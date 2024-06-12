package nl.quintor.recipe.integration;

import lombok.extern.slf4j.Slf4j;
import nl.quintor.recipe.ingredient.Ingredient;
import nl.quintor.recipe.ingredient.IngredientService;
import nl.quintor.recipe.recipe.Recipe;
import nl.quintor.recipe.recipe.RecipeRepository;
import nl.quintor.recipe.recipe.RecipeService;
import nl.quintor.recipe.recipe.dto.RecipeCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientService ingredientService;

    @Test
    public void getRecipeById() {
        webTestClient.get().uri("/recipe/1").exchange().expectStatus().isOk();
    }


    /**
     * TODO: needs to compare the exact contents of the ingredients rather than just checking on size
     */
    @Test
    public void createValidRecipeShouldReturnIsCreated() {
        var ingredients = ingredientService.readAllIngredients();
        var originalRecipes = (ArrayList<Recipe>) recipeRepository.findAll();
        var ham = ingredients.stream().filter(i -> i.getName().equalsIgnoreCase("ham")).findFirst().get();
        var cheese = ingredients.stream().filter(i -> i.getName().equalsIgnoreCase("cheese")).findFirst().get();
        var hamAndCheese = Set.of(ham, cheese);

        var special1 = new Recipe(null, "special 1", 12, "mix and bake", hamAndCheese);
        var special1Request = new RecipeCreateRequest(special1.getName(), special1.getServings(), special1.getInstructions(),
                hamAndCheese.stream().map(Ingredient::getId).collect(Collectors.toSet()));

        webTestClient.post()
                .uri("/recipe")
                .body(fromValue(special1Request))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo(special1.getName())
                .jsonPath("$.servings").isEqualTo(special1.getServings())
                .jsonPath("$.ingredients.length()").isEqualTo(hamAndCheese.size());

        var updatedRecipes = (ArrayList<Recipe>) recipeRepository.findAll();

        assertThat(updatedRecipes.size()).isEqualTo(originalRecipes.size() + 1);

    }

    /**
     *
     */
    @Test
    public void recipeWithoutNameShouldReturnError() {
        var ingredients = ingredientService.readAllIngredients();
        var originalRecipes = (ArrayList<Recipe>) recipeRepository.findAll();
        var ham = ingredients.stream().filter(i -> i.getName().equalsIgnoreCase("ham")).findFirst().get();
        var cheese = ingredients.stream().filter(i -> i.getName().equalsIgnoreCase("cheese")).findFirst().get();
        var hamAndCheese = Set.of(ham, cheese);

        var special1 = new Recipe(null, "special 1", 12, "mix and bake", hamAndCheese);

        // Note that the name is a blank strng here.
        var special1Request = new RecipeCreateRequest("", special1.getServings(), special1.getInstructions(),
                hamAndCheese.stream().map(Ingredient::getId).collect(Collectors.toSet()));

        webTestClient.post()
                .uri("/recipe")
                .body(fromValue(special1Request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").value(v -> {
                    var msg = (String) v;
                    assertThat(msg).isEqualToIgnoringCase("A name is required and cant consist solely of whitespace.");
                });

        var updatedRecipes = (ArrayList<Recipe>) recipeRepository.findAll();
        assertThat(updatedRecipes.size()).isEqualTo(originalRecipes.size());
    }

    @Test
    public void getRecipeByNonExistingIdShouldReturnNotFound() {
        var id = 1000;
        var path = "/recipe/" + id;

        // make sure that the id being used actually isn't associated with any recipe
        var f = recipeRepository.findById(id);
        assertThat(f).isEmpty();

        webTestClient.get()
                .uri(path)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void getRecipeWithIncludingIngredient() {
        var includesString = "cheese";
        var recipes = (List<Recipe>) recipeRepository.findAll();
        var recipesContainingCheese = recipes
                .stream()
                .filter(r -> {
                    return r.getIngredients()
                            .stream()
                            .anyMatch(i -> i.getName().equalsIgnoreCase(includesString));
                }).collect(Collectors.toSet());
        var amountOfRecipesContainingCheese = recipesContainingCheese.size();

        // make sure there is a recipe containing cheese in the database
        assertThat(amountOfRecipesContainingCheese).isGreaterThan(0);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipe")
                        .queryParam("includesFilter", includesString)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(amountOfRecipesContainingCheese);
    }

    @Test
    public void getRecipeWithIncludingIngredientAndVegetarian() {
        var ingredient = "egg";
        var recipes = (List<Recipe>) recipeRepository.findAll();
        var vegetarianEggRecipe = recipes
                .stream()
                .filter(r -> {
                    return r.getIngredients()
                            .stream()
                            .anyMatch(i -> i.getName().equalsIgnoreCase(ingredient));
                })
                .filter(r ->{
                    return r.getIngredients()
                            .stream()
                            .allMatch(i -> i.getIsVegetarian());
                }).collect(Collectors.toSet());
        var amountOfVegetarianEggRecipes = vegetarianEggRecipe.size();

        // make sure there is a recipe that is vegetarian and contains egg in the database
        assertThat(amountOfVegetarianEggRecipes).isGreaterThan(0);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipe")
                        .queryParam("includesFilter", ingredient)
                        .queryParam("vegetarianFilter", "true")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(amountOfVegetarianEggRecipes);
    }

    @Test
    public void getAllRecipes() {
        webTestClient.get()
                .uri("/recipe")
                .exchange()
                .expectStatus()
                .isOk();
    }
}

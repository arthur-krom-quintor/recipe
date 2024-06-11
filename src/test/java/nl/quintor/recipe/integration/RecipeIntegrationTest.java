package nl.quintor.recipe.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getRecipeById(){
        webTestClient.get().uri("/recipe/1").exchange().expectStatus().isOk();
    }

    @Test
    public void getRecipeByNonExistingIdShouldReturnNotFound(){
        webTestClient.get().uri("/recipe/13398").exchange().expectStatus().isNotFound();
    }

    @Test
    public void getAllRecipes(){
        webTestClient.get().uri("/recipe").exchange().expectStatus().isOk();
    }
}

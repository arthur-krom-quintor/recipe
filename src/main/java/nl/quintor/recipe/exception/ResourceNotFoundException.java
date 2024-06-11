package nl.quintor.recipe.exception;

public class ResourceNotFoundException extends RuntimeException{
    public static final String INGREDIENT_NOT_FOUND = "Couldn't find ingredient";
    public static final String RECIPE_NOT_FOUND = "Couldn't find recipe";

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

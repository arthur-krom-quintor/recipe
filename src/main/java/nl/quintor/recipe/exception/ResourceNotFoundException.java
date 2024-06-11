package nl.quintor.recipe.exception;

public class ResourceNotFoundException extends RuntimeException{
    public static final String INGREDIENT_NOT_FOUND = "Couldn't find ingredient";

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

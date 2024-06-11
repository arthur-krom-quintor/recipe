package nl.quintor.recipe.exception;

public class DuplicateException extends RuntimeException{
    public static final String UNIQUE_RECIPE_NAME = "Recipe name must be unique";

    public DuplicateException(String message) {
        super(message);
    }
}

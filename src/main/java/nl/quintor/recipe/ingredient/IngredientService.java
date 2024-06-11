package nl.quintor.recipe.ingredient;

import nl.quintor.recipe.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Set<Ingredient> readAllIngredients() {
        var result = new HashSet<Ingredient>();
        var found = ingredientRepository.findAll();
        found.forEach(result::add);
        return result;
    }

    public Ingredient readIngredientById(Integer id){
        var optionalIngredient =  ingredientRepository.findById(id);
        return optionalIngredient.orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundException.INGREDIENT_NOT_FOUND));
    }
}

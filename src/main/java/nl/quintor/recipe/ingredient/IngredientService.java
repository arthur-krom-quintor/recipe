package nl.quintor.recipe.ingredient;

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
        ingredientRepository.findAll().forEach(i ->{
            result.add(i);
        });

        return result;
    }
}

package guru.springframework.bootstrap;

import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.NotesRepository;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final NotesRepository notesRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public DataLoader(NotesRepository notesRepository, RecipeRepository recipeRepository,
                      IngredientRepository ingredientRepository) {
        this.notesRepository = notesRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) {
        Notes notes = new Notes();
        notes.setRecipeNotes("Some cool recipy notes");
        notesRepository.save(notes);

        Recipe recipe = new Recipe();
        recipe.setCookTime(60);
        recipe.setDescription("Cool recipe decription");
        recipe.setPrepTime(120);
        recipe.setSource("Some cool recipe book");
        recipe.setDescription("Recipe description");
        recipeRepository.save(recipe);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setDescription("Sugar");
        ingredient1.setAmount(new BigDecimal(100));
        ingredient1.setRecipe(recipe);
        ingredientRepository.save(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setDescription("Salt");
        ingredient2.setAmount(new BigDecimal(50));
        ingredient2.setRecipe(recipe);
        ingredientRepository.save(ingredient2);

        notes.setRecipe(recipe);
        notesRepository.save(notes);

        recipe.setNotes(notes);
        recipe.setIngredients(Set.of(ingredient1, ingredient2));
        recipeRepository.save(recipe);

        ingredient1.setRecipe(recipe);
        ingredientRepository.save(ingredient1);
        ingredient2.setRecipe(recipe);
        ingredientRepository.save(ingredient2);
    }
}

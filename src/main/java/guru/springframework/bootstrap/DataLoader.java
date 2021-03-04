package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final NotesRepository notesRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(NotesRepository notesRepository, RecipeRepository recipeRepository,
                      IngredientRepository ingredientRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository,
                      CategoryRepository categoryRepository) {
        this.notesRepository = notesRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
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
        recipe.setDifficulty(Difficulty.MODERATE);
        recipeRepository.save(recipe);

        Category category1 = new Category();
        category1.setDescription("Baking");
        category1.setRecipes(Set.of(recipe));
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setDescription("Roasting");
        category2.setRecipes(Set.of(recipe));
        categoryRepository.save(category2);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setDescription("Sugar");
        ingredient1.setAmount(new BigDecimal(100));
        ingredient1.setRecipe(recipe);
        ingredientRepository.save(ingredient1);

        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setDescription("cup");
        unitOfMeasureRepository.save(unitOfMeasure1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setDescription("Salt");
        ingredient2.setAmount(new BigDecimal(50));
        ingredient2.setRecipe(recipe);
        ingredientRepository.save(ingredient2);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setDescription("spoon");
        unitOfMeasureRepository.save(unitOfMeasure2);

        notes.setRecipe(recipe);
        notesRepository.save(notes);

        recipe.setNotes(notes);
        recipe.setIngredients(Set.of(ingredient1, ingredient2));
        recipe.setCategories(Set.of(category1, category2));
        recipeRepository.save(recipe);

        ingredient1.setRecipe(recipe);
        ingredient1.setUom(unitOfMeasure1);
        ingredientRepository.save(ingredient1);
        ingredient2.setRecipe(recipe);
        ingredient2.setUom(unitOfMeasure2);
        ingredientRepository.save(ingredient2);
    }
}
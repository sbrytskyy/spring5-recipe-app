package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
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
    @Transactional
    public void run(String... args) {
        createSomeUoMs();
        createSomeCategories();

        Recipe recipe = getRecipe();
        recipeRepository.save(recipe);
    }

    private Recipe getRecipe() {
        Notes notes = new Notes();
        notes.setRecipeNotes("Some cool recipe notes");

        Recipe recipe = new Recipe();
        recipe.setCookTime(60);
        recipe.setDescription("Cool recipe description");
        recipe.setPrepTime(120);
        recipe.setSource("Some cool recipe book");
        recipe.setDescription("Recipe description");
        recipe.setDifficulty(Difficulty.MODERATE);
        recipe.setNotes(notes);
        notes.setRecipe(recipe);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setDescription("Sugar");
        ingredient1.setAmount(new BigDecimal(100));
        ingredient1.setRecipe(recipe);

        Optional<UnitOfMeasure> unitOfMeasure1 = unitOfMeasureRepository.findByDescriptionStartsWith("Oun");
        unitOfMeasure1.ifPresent(ingredient1::setUom);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setDescription("Salt");
        ingredient2.setAmount(new BigDecimal(50));
        ingredient2.setRecipe(recipe);

        Optional<UnitOfMeasure> unitOfMeasure2 = unitOfMeasureRepository.findByDescriptionStartsWith("Tea");
        unitOfMeasure2.ifPresent(ingredient2::setUom);

        recipe.setIngredients(Set.of(ingredient1, ingredient2));

        Optional<Category> category1 = categoryRepository.findByDescriptionStartsWith("Am");
        Optional<Category> category2 = categoryRepository.findByDescriptionStartsWith("Ba");
        category1.ifPresent(recipe.getCategories()::add);
        category2.ifPresent(recipe.getCategories()::add);
        return recipe;
    }

    private void createSomeUoMs() {
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setDescription("cup");
        unitOfMeasureRepository.save(unitOfMeasure1);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setDescription("spoon");
        unitOfMeasureRepository.save(unitOfMeasure2);
    }

    private void createSomeCategories() {
        Category category1 = new Category();
        category1.setDescription("Baking");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setDescription("Roasting");
        categoryRepository.save(category2);
    }
}

package guru.springframework.bootstrap;

import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.NotesRepository;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final NotesRepository notesRepository;
    private final RecipeRepository recipeRepository;

    public DataLoader(NotesRepository notesRepository, RecipeRepository recipeRepository) {
        this.notesRepository = notesRepository;
        this.recipeRepository = recipeRepository;
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

        recipe.setNotes(notes);
        notes.setRecipe(recipe);

        notesRepository.save(notes);
        recipeRepository.save(recipe);
    }
}

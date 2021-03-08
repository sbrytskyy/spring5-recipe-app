package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipeById() {
        Recipe recipeExpected = new Recipe();
        recipeExpected.setId(1L);
        recipeExpected.setCookTime(60);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipeExpected));

        Recipe recipe = recipeService.getRecipe(1L);

        assertEquals(recipe, recipeExpected);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        when(recipeRepository.findAll()).thenReturn(Set.of(new Recipe()));

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

}
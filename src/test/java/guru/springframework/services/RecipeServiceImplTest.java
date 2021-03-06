package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    private RecipeService recipeService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(recipeRepository.findAll()).thenReturn(Set.of(new Recipe()));

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getAllRecipes() {
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }
}
package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    @Mock
    Model model;
    @Mock
    private RecipeService recipeService;
    private IndexController indexController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        indexController = new IndexController(recipeService);
    }

    @Test
    public void getIndexPage() {
        Set<Recipe> recipes = Set.of(new Recipe());
        when(recipeService.getRecipes()).thenReturn(recipes);
        when(model.getAttribute("recipes")).thenReturn(recipes);

        String indexPage = indexController.getIndexPage(model);

        assertEquals(indexPage, "index");
        assertEquals(model.getAttribute("recipes"), recipes);
        verify(model, times(1)).addAttribute(eq("recipes"), anyIterable());
        verify(model, times(0)).addAttribute(AdditionalMatchers.not(eq("recipes")), anyIterable());
        verify(recipeService, times(1)).getRecipes();
    }
}
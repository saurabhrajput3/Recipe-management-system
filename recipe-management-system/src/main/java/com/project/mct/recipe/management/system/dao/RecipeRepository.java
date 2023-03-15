package com.project.mct.recipe.management.system.dao;

import com.project.mct.recipe.management.system.model.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe,Integer> {

    @Query(value = "select * from tbl_recipe where recipe_id=:recipeId ",nativeQuery = true)
    public List<Recipe> getRecipeById(Integer recipeId);

    @Query(value = "select * from tbl_recipe where recipe_name=:recipeName",nativeQuery = true)
    public Recipe getRecipeByName(String recipeName);


}

package com.project.mct.recipe.management.system.service;

import com.project.mct.recipe.management.system.dao.RecipeRepository;
import com.project.mct.recipe.management.system.model.Recipe;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;
    public int saveRecipe(Recipe recipe) {
        Recipe recipeObj=recipeRepository.save(recipe);
        return recipeObj.getRecipeId();
    }


    public JSONObject updateRecipe(String recipeId, Recipe newRecipe) {
        List<Recipe> recipe=recipeRepository.getRecipeById(Integer.valueOf(recipeId));
        JSONObject obj=new JSONObject();

        if(!recipe.isEmpty()){
            Recipe oldRecipe= recipe.get(0);
            newRecipe.setOwnerId(oldRecipe.getOwnerId());
            newRecipe.setCreatedDate(oldRecipe.getCreatedDate());
            newRecipe.setRecipeName(oldRecipe.getRecipeName());

            Timestamp updatedTime=new Timestamp(System.currentTimeMillis());
            newRecipe.setUpdatedDate(updatedTime);

            recipeRepository.save(newRecipe);
        }
        else {
            obj.put("errorMessage","Recipe doesn't exist");
        }
        return obj;
    }


    public JSONObject getRecipeById(String recipeId) {
        List<Recipe> obj =recipeRepository.getRecipeById(Integer.valueOf(recipeId));
        JSONObject response=new JSONObject();
        response=createResponse(obj.get(0));
      return response;
    }


    public JSONObject getRecipeByName(String recipeName) {
        Recipe recipe=recipeRepository.getRecipeByName(recipeName);
        JSONObject response=new JSONObject();
        response=createResponse(recipe);

        return response;
    }

    public void deleteRecipeById(int recipeId) {
        recipeRepository.deleteById(recipeId);
    }


    private JSONObject createResponse(Recipe recipe) {
        JSONObject obj=new JSONObject();

        obj.put("recipeId",recipe.getRecipeId());
        obj.put("recipeName",recipe.getRecipeName());
        obj.put("recipeInstruction",recipe.getRecipeInstruction());
        obj.put("recipeIngredients",recipe.getRecipeIngredients());
        obj.put("created_date",recipe.getCreatedDate());
        obj.put("updated_date",recipe.getUpdatedDate());
        obj.put("owner_id",recipe.getOwnerId());
        obj.put("updated_user_id",recipe.getUpdatedId());

        return obj;

    }
}

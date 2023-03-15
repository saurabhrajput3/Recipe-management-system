package com.project.mct.recipe.management.system.controller;

import com.project.mct.recipe.management.system.dao.UsersRepository;
import com.project.mct.recipe.management.system.model.Recipe;
import com.project.mct.recipe.management.system.model.Users;
import com.project.mct.recipe.management.system.service.RecipeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping(value = "/api/recipe")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    UsersRepository usersRepository;
    @PostMapping(value = "/create-recipe")
    public ResponseEntity<String> createRecipe(@RequestBody String recipeBody){
        JSONObject object=new JSONObject(recipeBody);
        JSONObject error=validateRecipe(object);

        if(error.isEmpty()){
            Recipe recipe=setRecipe(object);
            int recipeId=recipeService.saveRecipe(recipe);
            return new ResponseEntity<>("Recipe created-"+recipeId, HttpStatus.CREATED);

        }
        else{
            return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
        }


    }
    @PutMapping(value ="/update-recipe/{recipeId}")
    public ResponseEntity<String> updateRecipe(@PathVariable String recipeId, @RequestBody String recipeBody){
       JSONObject jsonObject=new JSONObject(recipeBody);
        JSONObject isValid= validateRecipe(jsonObject);
        Recipe recipe=null;

        if(isValid.isEmpty()){
            recipe=setRecipe(jsonObject);
            JSONObject response=recipeService.updateRecipe(recipeId,recipe);
            if(response.has("errorMessage")){
                return new ResponseEntity<String>(response.toString(),HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<String>(isValid.toString(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("recipe updated",HttpStatus.OK);

    }


    @GetMapping(value = "/get-recipe-id")
    public ResponseEntity<String> getRecipeById(@RequestParam String recipeId){
        JSONObject response=recipeService.getRecipeById(recipeId);
        return new ResponseEntity<String>(response.toString(),HttpStatus.OK);
    }

    @GetMapping(value = "/get-recipe-name")
    public ResponseEntity<String> getRecipeByName(@RequestParam String recipeName){
        JSONObject response=recipeService.getRecipeByName(recipeName);
        return new ResponseEntity<String>(response.toString(),HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete-recipe/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int recipeId){
        recipeService.deleteRecipeById(recipeId);
        return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    }







    private Recipe setRecipe(JSONObject object) {
        Recipe recipe=new Recipe();

        if(!object.has("isUpdate")) {
            recipe.setRecipeName(object.getString("recipeName"));
        }

        recipe.setRecipeInstruction(object.getString("recipeInstruction"));
        recipe.setRecipeIngredients(object.getString("recipeIngredients"));

        Timestamp createdTime=new Timestamp(System.currentTimeMillis());
        recipe.setCreatedDate(createdTime);

        if(!object.has("isUpdate")) {
            int ownerId = object.getInt("ownerId");
            Users user = usersRepository.findById(ownerId).get();
            recipe.setOwnerId(user);
        }

        if(object.has("isUpdate")) {
            int updatedId = object.getInt("updatedId");
            Users user = usersRepository.findById(updatedId).get();
            recipe.setUpdatedId(user);
        }

return recipe;
    }

    private JSONObject validateRecipe(JSONObject object) {
        JSONObject error=new JSONObject();

        if(!object.has("isUpdate")) {
            if (!object.has("recipeName")) {
                error.put("recipeName", "missing parameter");
            }
        }
        if(object.has("recipeInstruction")){
            String recipeInstruction=object.getString("recipeInstruction");
            if(recipeInstruction.isEmpty()||recipeInstruction.isBlank()){
                error.put("recipeInstruction","recipeInstruction body can't be empty");
            }
        }
        else{
            error.put("recipeInstruction","missing parameter");
        }

        if(object.has("recipeIngredients")){
            String recipeIngredients=object.getString("recipeIngredients");
            if(recipeIngredients.isEmpty()||recipeIngredients.isBlank()){
                error.put("recipeIngredients","recipeIngredients body can't be empty");
            }
        }
        else{
            error.put("recipeIngredients","missing parameter");
        }

        return error;
    }


}

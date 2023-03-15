package com.project.mct.recipe.management.system.controller;

import com.project.mct.recipe.management.system.dao.StatusRepository;
import com.project.mct.recipe.management.system.dao.UsersRepository;
import com.project.mct.recipe.management.system.model.Status;
import com.project.mct.recipe.management.system.model.Users;
import com.project.mct.recipe.management.system.service.UsersService;
import com.project.mct.recipe.management.system.util.CommanUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UsersController {
    @Autowired
    UsersService usersService;

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    StatusRepository statusRepository;
    @PostMapping(value = "/create-user")
    public ResponseEntity<String> createUser(@RequestBody String userData){
        JSONObject isValid= validateUser(userData);

        Users user=null;

        if(isValid.isEmpty()){
                user=setUser(userData);
                usersService.saveUser(user);
        }
        else{
            return new ResponseEntity<String>(isValid.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Saved",HttpStatus.CREATED);
    }


    @GetMapping(value = "/get-users")
    public ResponseEntity<String> getUsers(@Nullable @RequestParam String userId){
        JSONArray userArr=usersService.getUsers(userId);

        return new ResponseEntity<>(userArr.toString(),HttpStatus.OK);

    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody String requestData){
        JSONObject requestJson= new JSONObject(requestData);

        JSONObject isValidLogin= validateLogin(requestJson);

        if(isValidLogin.isEmpty()){
            String username=requestJson.getString("username");
            String password=requestJson.getString("password");
            JSONObject responseObj=usersService.login(username,password);
            if(responseObj.has("errorMessage")){
                return new ResponseEntity<String>(responseObj.toString(),HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<String>(responseObj.toString(),HttpStatus.OK);
            }
        }
        else{
            return new ResponseEntity<String>(isValidLogin.toString(),HttpStatus.BAD_REQUEST);
        }


    }


    @PutMapping(value ="/update-user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId,@RequestBody String requestData){
        JSONObject isRequestValid= validateUser(requestData);
        Users user=null;

        if(isRequestValid.isEmpty()){
            user =setUser(requestData);
            JSONObject responseObj=usersService.updateUser(user, userId);
            if(responseObj.has("errorMessage")){
                return new ResponseEntity<String>(responseObj.toString(),HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<String>(isRequestValid.toString(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("user updated",HttpStatus.OK);


    }


    @DeleteMapping(value = "/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId){
        usersService.deleteUserByUserId(userId);
        return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    }







    private JSONObject validateLogin(JSONObject requestJson){
        JSONObject errorList=new JSONObject();

        if(!requestJson.has("username")){
            errorList.put("username","Missing Parameter");
        }
        if(!requestJson.has("password")){
            errorList.put("password","Missing Parameter");
        }
        return errorList;
    }




    private JSONObject validateUser(String userData) {
            JSONObject userJson=new JSONObject(userData);
            JSONObject error=new JSONObject();

            if(!userJson.has("isUpdate")){
                if(userJson.has("username")){
                    String username=userJson.getString("username");
                    List<Users> usersList= usersRepository.findByUsername(username);
                    if(usersList.size()>0){
                        error.put("username","username is already exists");
                    }
                }
                else{
                    error.put("username","missing parameter");
                }
            }

            if(!userJson.has("isUpdate")){
                if(userJson.has("password")){
                    String password=userJson.getString("password");
                    if(!CommanUtils.isValidPassword(password)){
                            error.put("password","enter valid password");
                    }

                }
                else{
                    error.put("password","missing parameter");
                }
            }

            if(userJson.has("firstName")){
                String firstName=userJson.getString("firstName");
            }
            else{
                error.put("firstName","Missing Parameter");
            }

            if(userJson.has("lastName")){
                String lastName=userJson.getString("lastName");
            }
            else{
                error.put("lastName","Missing Parameter");
            }

            if(userJson.has("email")){
                String email=userJson.getString("email");
                if(!CommanUtils.isValidEmail(email)){
                    error.put("email","Enter valid email");
                }
            }
            else{
                error.put("email","Missing parameter");
            }

            if(userJson.has("phoneNumber")){
                String phoneNumber=userJson.getString("phoneNumber");
                if(!CommanUtils.isValidPhoneNumber(phoneNumber)){
                    error.put("phoneNumber","Enter valid phoneNumber");
                }
            }
            else{
                error.put("phoneNumber","Missing parameter");
            }

            return error;
    }


    private Users setUser(String userData) {
        JSONObject userJson=new JSONObject(userData);
        Users user=new Users();

        if(!userJson.has("isUpdate")) {
            user.setUsername(userJson.getString("username"));
            user.setPassword(userJson.getString("password"));
        }

        user.setEmail(userJson.getString("email"));
        user.setFirstName(userJson.getString("firstName"));
        user.setLastName(userJson.getString("lastName"));
        user.setPhoneNumber(userJson.getString("phoneNumber"));

        if(userJson.has("gender")){
            user.setGender(userJson.getString("gender"));
        }

        if(userJson.has("age")){
            user.setAge(userJson.getInt("age"));
        }

        if(!userJson.has("isUpdate")) {
            Timestamp createdTime = new Timestamp(System.currentTimeMillis());
            user.setCreatedDate(createdTime);
        }

        Status status=statusRepository.findById(1).get();
        user.setStatusId(status);

        return user;
    }

}

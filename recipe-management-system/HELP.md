# Framework: SpringBoot
# Language: Java
# database: Mysql
# Cloud: AWS

# Data Flow:
## 1) Controller: 

## a)Recipe Controller:
### create recipe: 
      @PostMapping(value = "/create-recipe")
    public ResponseEntity<String> createRecipe(@RequestBody String recipeBody)
    
### update recipe by recipeId: 
      @PutMapping(value ="/update-recipe/{recipeId}")
    public ResponseEntity<String> updateRecipe(@PathVariable String recipeId, @RequestBody String recipeBody)
                             
### get recipe by id:  
 @GetMapping(value = "/get-recipe-id")
    public ResponseEntity<String> getRecipeById(@RequestParam String recipeId)
                  
### get recipe by name: 
 @GetMapping(value = "/get-recipe-name")
    public ResponseEntity<String> getRecipeByName(@RequestParam String recipeName)
                  
###  delete recipe by recipeId: 
 @DeleteMapping(value = "/delete-recipe/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int recipeId)


 ## b)status Controller:
  ###  create status: 
 @PostMapping("/create-status")
    public ResponseEntity<String> createStatus(@RequestBody String statusData)
  
  
 ## b)Users Controller:
  ###  create user: 
 @PostMapping(value = "/create-user")
    public ResponseEntity<String> createUser(@RequestBody String userData)
  
  ###  get users by id: 
 @GetMapping(value = "/get-users")
    public ResponseEntity<String> getUsers(@Nullable @RequestParam String userId)
  
  ###  users login: 
@PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody String requestData)
  
  ###  update user: 
@PutMapping(value ="/update-user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId,@RequestBody String requestData)
  
  ###  delete user: 
 @DeleteMapping(value = "/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId)

 
 ## 2) Service:
  
 ### a) Recipe service:
 
 ### save recipe:
  public int saveRecipe(Recipe recipe)
  
 ### update recipe:
     public JSONObject updateRecipe(String recipeId, Recipe newRecipe)
 
### get recipe by id:
 public JSONObject getRecipeById(String recipeId) 
  
 ### get recipe by name:
 public JSONObject getRecipeByName(String recipeName)
  
 ### delete recipe:
 public void deleteRecipeById(int recipeId)
  
  
  ### b) status service:
  
  ### save status:
  public int saveStatus(Status status)
  
  
  ### c) users service:
  
  
### save user:
 public int saveUser(Users user) 
  
 ### get user by id:
 public JSONArray getUsers(String userId) 
  
  ### get login:
 public JSONObject login(String username, String password)
  
 ### update user:
  public JSONObject updateUser(Users newUser, String userId) 
  
  ### delete user:
  public void deleteUserByUserId(int userId)
  
  
  
  ## 3) Repository
  
  ### a) recipe repo:
  
  ## get recipe by id
   @Query(value = "select * from tbl_recipe where recipe_id=:recipeId ",nativeQuery = true)
    public List<Recipe> getRecipeById(Integer recipeId);

  ## get recipe by name
    @Query(value = "select * from tbl_recipe where recipe_name=:recipeName",nativeQuery = true)
    public Recipe getRecipeByName(String recipeName);
  
  
   ### a) user repo:
  
  ## find user by username
  @Query(value="Select * from tbl_user where username=:username and status_id=1",nativeQuery = true)
    public List<Users> findByUsername(String username);
  
  ## get user by id
    @Query(value="Select * from tbl_user where user_id=:userId and status_id=1",nativeQuery = true)
    public List<Users> getUserByUserId(int userId);

  ## get all users
    @Query(value="Select * from tbl_user where status_id=1",nativeQuery = true)
    public List<Users> getAllUsers();
  
  ## delete user (soft deleting)
    @Modifying
    @Transactional
    @Query(value = "update tbl_user set status_id = 2 where user_id=:userId ",
            countQuery = "Select count(*) FROM tbl_user", nativeQuery = true)
    public void deleteUserByUserId(int userId);
  
  
  
### Project Summary :
  
  created a recipe management system project 
  here we can create user, delete user, update user, login user, get users by id or name
  we can create recipe, dlete recipe, update recipe, get recipe by name or id
  
  
 
 

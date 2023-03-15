package com.project.mct.recipe.management.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_recipe")
public class Recipe {
    @Id
    @Column(name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeId;
    @Column(name = "recipe_name")
    private String recipeName;
    @Column(name = "recipe_instruction")
    private String recipeInstruction;
    @Column(name = "recipe_ingredients")
    private String recipeIngredients;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users ownerId;
    @ManyToOne
    @JoinColumn(name = "updated_user_id")
    private Users updatedId;


}

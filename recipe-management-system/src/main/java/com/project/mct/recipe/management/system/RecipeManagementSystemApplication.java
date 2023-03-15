package com.project.mct.recipe.management.system;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class RecipeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeManagementSystemApplication.class, args);
	}

}

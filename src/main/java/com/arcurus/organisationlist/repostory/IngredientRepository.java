package com.arcurus.organisationlist.repostory;

import com.arcurus.organisationlist.model.Ingredient;
import com.arcurus.organisationlist.model.Menus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    @Query("SELECT i FROM Ingredient i JOIN i.menus m where m.ingredient=:ingredient")
    List<Ingredient> findByIngredientId(@Param("id")long ingredientId);
}

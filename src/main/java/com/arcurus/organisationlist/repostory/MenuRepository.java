package com.arcurus.organisationlist.repostory;

import com.arcurus.organisationlist.model.Menus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menus,Long> {
    @Query("SELECT m FROM Menus m JOIN m.restaurant r WHERE r.id = :restaurantId")
    List<Menus>findByRestaurantId(@Param("restaurantId")long restaurantId);
    Optional<Menus> findMenusByFoodName(String name);

}

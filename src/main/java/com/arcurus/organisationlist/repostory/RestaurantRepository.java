package com.arcurus.organisationlist.repostory;

import com.arcurus.organisationlist.model.Contacts;
import com.arcurus.organisationlist.model.Restaurant;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
      @Query("SELECT r FROM Restaurant r JOIN r.menus m WHERE m.foodName = :foodName")
      List<Restaurant> findByMenuFoodName(@Param("foodName") String foodName);

      @Query("SELECT r FROM Restaurant r JOIN r.menus p  WHERE p.foodPrice= :price")
      List<Restaurant>findByMenuPrice(@Param("price")int price);

      @Query("SELECT r FROM Restaurant r JOIN r.reviews ra  WHERE ra.rating=:rating")
      List<Restaurant>findRestaurantByRating(@Param("rating")int rating);
      @Query("SELECT r, COUNT(rv) FROM Restaurant r LEFT JOIN r.reviews rv WHERE r.id = :restaurantId")
      Long countReviewsByRestaurant(@Param("restaurantId") Long restaurantId);
      Restaurant findRestaurantByContacts(Contacts contact) ;
      Optional<Restaurant> findRestaurantByNameIgnoreCase(String name);
}
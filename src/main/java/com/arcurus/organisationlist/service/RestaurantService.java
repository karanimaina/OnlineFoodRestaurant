package com.arcurus.organisationlist.service;

import com.arcurus.organisationlist.model.Images;
import com.arcurus.organisationlist.wrappers.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface RestaurantService  {
    Mono<UniversalResponse> addRestaurant(RestaurantWrapper restaurant);

    Mono<UniversalResponse> addContacts(ContactWrapper contactWrapper, String name);

    Mono<UniversalResponse> addPayment(PaymentWrapper paymentWrapper, String name);

    Mono<UniversalResponse> getRestaurantsByFoodName(String name);

    Mono<UniversalResponse> getRestaurantsByFoodPrice(int price);

    Mono<UniversalResponse> getRestaurantsByRating(long res);

    Mono<UniversalResponse> getRestaurantsByReviews(long restId);

    //     menu
    Mono<UniversalResponse> addMenu(MenuWrapper menuWrapper);
    Mono<UniversalResponse> addIngredientsToMenu(String menuName, List<IngredientWrapper>ingredientWrappers);
    Mono<UniversalResponse>editMenuIngredients(String menuName,List<IngredientWrapper>ingredients);

    Mono<UniversalResponse> editMenu(MenuWrapper menuWrapper);

    Mono<UniversalResponse> deleteMenu(String name);
    Mono<UniversalResponse>addMenuToRestaurants(String menuName,String restaurant);

    Mono<UniversalResponse> readMenu( String restaurantName);

    //     contact

    Mono<UniversalResponse> editContacts(ContactWrapper contactWrapper, String restaurantname);
    Mono<UniversalResponse> deleteContact(long contactId);

    Mono<UniversalResponse> readContacts(long restaurantId);

    //     Ingredient

    Mono<UniversalResponse> deleteIngredientFromMenu(long menuId,String ingredientName) ;

      Set<Images> uploadImage(MultipartFile[]  multipartFiles) throws IOException;

}

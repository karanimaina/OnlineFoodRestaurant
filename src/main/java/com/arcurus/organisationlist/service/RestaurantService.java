package com.arcurus.organisationlist.service;

import com.arcurus.organisationlist.model.Ingredient;
import com.arcurus.organisationlist.model.Restaurant;
import com.arcurus.organisationlist.wrappers.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RestaurantService {
    Mono<UniversalResponse> addRestaurant(RestaurantWrapper restaurant);

    Mono<UniversalResponse> addContacts(ContactWrapper contactWrapper, String name);

    Mono<UniversalResponse> addPayment(PaymentWrapper paymentWrapper, String name);

    Mono<UniversalResponse> getRestaurantsByFoodName(String name);

    Mono<UniversalResponse> getRestaurantsByFoodPrice(int price);

    Mono<UniversalResponse> getRestaurantsByRating(int rating);

    Mono<UniversalResponse> getRestaurantsByReviews(long restId);

    //     menu
    Mono<UniversalResponse> addMenu(MenuWrapper menuWrapper);
    Mono<UniversalResponse>addIngredients(long menuId, IngredientWrapper ingredientWrapper);

    Mono<UniversalResponse> editMenu(MenuWrapper menuWrapper);

    Mono<UniversalResponse> deleteMenu();

    Mono<UniversalResponse> readMenu();

    //     contact
    Mono<UniversalResponse> addContacts(ContactWrapper contactWrapper);

    Mono<UniversalResponse> editContacts(ContactWrapper contactWrapper, long restaurantId);

    Mono<UniversalResponse> deleteContact(long contactId);

    Mono<UniversalResponse> readContacts(long restaurantId);

    //     Ingredient
    Mono<UniversalResponse> addIngredients(IngredientWrapper ingredientWrapper);

    Mono<UniversalResponse> editIngredient(IngredientWrapper ingredientWrapper);

    Mono<UniversalResponse> deleteIngredient(long id);

    Mono<UniversalResponse> getIngredient(long menuId);

}

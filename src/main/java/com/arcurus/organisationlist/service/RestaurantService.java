package com.arcurus.organisationlist.service.service;

import com.arcurus.organisationlist.model.Restaurant;
import com.arcurus.organisationlist.wrappers.RestaurantWrapper;
import com.arcurus.organisationlist.wrappers.UniversalResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RestaurantService {
    Mono<UniversalResponse>addRestaurant(RestaurantWrapper restaurant);
    Flux<UniversalResponse> getRestaurantsByFoodName(String name);
    Flux<UniversalResponse>getRestaurantsByFoodPrice(int price);
     Flux<UniversalResponse>getRestaurantsByRating(int rating);
     Flux<UniversalResponse>getRestaurantsByReviews();

}

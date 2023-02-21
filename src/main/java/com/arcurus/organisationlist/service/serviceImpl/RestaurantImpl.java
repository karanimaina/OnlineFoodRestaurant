package com.arcurus.organisationlist.service.serviceImpl;
import com.arcurus.organisationlist.excepttions.ContactException;
import com.arcurus.organisationlist.excepttions.MenuException;
import com.arcurus.organisationlist.excepttions.PaymentException;
import com.arcurus.organisationlist.excepttions.RestaurantException;
import com.arcurus.organisationlist.model.Contacts;
import com.arcurus.organisationlist.model.Menus;
import com.arcurus.organisationlist.model.PaymentOption;
import com.arcurus.organisationlist.model.Restaurant;
import com.arcurus.organisationlist.repostory.ContactRepository;
import com.arcurus.organisationlist.repostory.MenuRepository;
import com.arcurus.organisationlist.repostory.PaymentRepository;
import com.arcurus.organisationlist.repostory.RestaurantRepository;
import com.arcurus.organisationlist.service.RestaurantService;
import com.arcurus.organisationlist.wrappers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.swing.event.ListDataEvent;
import java.awt.*;
import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantImpl implements RestaurantService {
    private  final RestaurantRepository restaurantRepository;
    private  final ContactRepository contactRepository;
    private  final PaymentRepository paymentRepository;
    private final MenuRepository menuRepository;

    @Override
    public Mono<UniversalResponse> addRestaurant(RestaurantWrapper restaurantWrapper) {

        return Mono.fromCallable(()->{
            Restaurant res = restaurantRepository.findRestaurantByNameIgnoreCase(restaurantWrapper.name()).orElseThrow(()-> new RestaurantException("a restaurant with the name" + restaurantWrapper.name()+ "already Exist"));
            Restaurant.builder()
                    .location(restaurantWrapper.location())
                    .name(restaurantWrapper.name())
                    .shippingCost(restaurantWrapper.shippingCost())
                    .build();
            restaurantRepository.save(res);
            return UniversalResponse.builder().status(200).message("a new restauarbt was created").data(res).build();

        }).publishOn(Schedulers.boundedElastic());
    }


    @Override
    public Mono<UniversalResponse> addContacts(ContactWrapper contactWrapper, String name) {
        return Mono.fromCallable(()->{
           Restaurant restaurant = restaurantRepository.findRestaurantByNameIgnoreCase(name).orElse(null);
           if (restaurant == null){
               throw  new RestaurantException("restaurant was not found");
           }
            Contacts contacts = contactRepository.findContactsByName(contactWrapper.name()).orElseThrow(()-> new ContactException("contact with this  datail was found"));
            if (contacts != null){
                throw new ContactException("cntact with the given name already exist");
            }
            Contacts contacts1 = Contacts.builder()
                    .website(contactWrapper.website())
                    .phoneNumber(contactWrapper.phoneNumber())
                    .name(contactWrapper.name())
                    .email(contactWrapper.email())
                    .build();
            contactRepository.save(contacts1);
            restaurant.setContacts(contacts1);
            restaurantRepository.save(restaurant);
            return UniversalResponse.builder().data(restaurant).status(200).message("contacts succesfully added").build();
        }).publishOn(Schedulers.boundedElastic());

    }

    @Override
    public Mono<UniversalResponse> addPayment(PaymentWrapper paymentWrapper,String name) {
        return Mono.fromCallable(()->{
            Restaurant restaurant = restaurantRepository.findRestaurantByNameIgnoreCase(name).orElse(null);
            if (restaurant == null){
                throw  new RestaurantException("restaurant was not found");
            }
           PaymentOption paymentOption =paymentRepository.findPaymentOptionByBankDetails(paymentWrapper.bankDetails());
            if (paymentOption != null){
                throw new PaymentException("the payment detail exist");
            }
            PaymentOption payment =PaymentOption.builder().paymentDetails(paymentWrapper.bankDetails()).build();
            paymentRepository.save(payment);
            restaurant.setPaymentOption(payment);
            restaurantRepository.save(restaurant);
            return UniversalResponse.builder().data(restaurant).status(200).message("contacts succesfully added").build();
        }).publishOn(Schedulers.boundedElastic());

    }
    @Override
    public Mono<UniversalResponse> getRestaurantsByFoodName(String name) {
        return Mono.fromCallable(() -> {
            List<Restaurant> restaurants = restaurantRepository.findByMenuFoodName(name);
            return UniversalResponse.builder().message("restaurants offering"+name).data(restaurants).build();
        }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UniversalResponse> getRestaurantsByFoodPrice(int price) {
        return Mono.fromCallable(() -> {
            List<Restaurant> restaurants = restaurantRepository.findByMenuPrice(price);
            return UniversalResponse.builder().data(restaurants).message("restaurant offering food at this" + price).build();
        }).publishOn(Schedulers.boundedElastic());
    }



    @Override
    public Mono<UniversalResponse> getRestaurantsByRating(int rating) {
        return Mono.fromCallable(() -> {
            List<Restaurant> restaurants = restaurantRepository.findRestaurantByRating(rating);
            return UniversalResponse.builder().data(restaurants).status(200).message("restaurants fetched by" + rating).build();

        }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono <UniversalResponse> getRestaurantsByReviews(long restaurantId) {
        return Mono.fromCallable(() -> {
            long reviews = restaurantRepository.countReviewsByRestaurant(restaurantId);
            return UniversalResponse.builder().message("restaurant has "+reviews).status(200).build();
        }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UniversalResponse> addMenu(MenuWrapper menuWrapper) {
        return Mono.fromCallable(()->{
            Menus menus = menuRepository.findMenusByFoodName(menuWrapper.foodName()).orElse(null);
            if (menus != null){
                throw  new MenuException(" well the menu for this food already exist");
            }
            Menus menus1 = Menus.builder().foodName(menuWrapper.foodName())
                    .preparationTime(menuWrapper.preparationTime())
                    .nutritionalInfo(menuWrapper.nutritionalInfo())
                    .build();
            menuRepository.save(menus1);
            return UniversalResponse.builder().status(200).message("menu created").data(menus1).build();
        }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UniversalResponse> addIngredients(long menuId, IngredientWrapper ingredientWrapper) {
        Mono.fromCallable(()->{
            Menus

        })
    }

    @Override
    public Mono<UniversalResponse> editMenu(MenuWrapper menuWrapper) {
        return null;
    }

    @Override
    public Mono<UniversalResponse> deleteMenu() {
        return null;
    }

    @Override
    public Mono<UniversalResponse> readMenu() {
        return null;
    }

    @Override
    public Mono<UniversalResponse> addContacts(ContactWrapper contactWrapper) {
        return null;
    }

    @Override
    public Mono<UniversalResponse> editContacts(ContactWrapper contactWrapper, long restaurantId) {
        return null;
    }

    @Override
    public Mono<UniversalResponse> deleteContact(long contactId) {
        return null;
    }

    @Override
    public Mono<UniversalResponse> readContacts(long restaurantId) {
        return null;
    }

    @Override
    public Mono<UniversalResponse> addIngredients(IngredientWrapper ingredientWrapper) {
        return null;
    }

    @Override
    public Mono<UniversalResponse> editIngredient(IngredientWrapper ingredientWrapper) {
        return null;
    }

    @Override
    public Mono<UniversalResponse> deleteIngredient(long id) {
        return null;
    }

    @Override
    public Mono<UniversalResponse> getIngredient(long menuId) {
        return null;
    }
}

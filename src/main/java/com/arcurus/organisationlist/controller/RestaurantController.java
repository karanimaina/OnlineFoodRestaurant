package com.arcurus.organisationlist.controller;

import com.arcurus.organisationlist.model.Images;
import com.arcurus.organisationlist.model.Menus;
import com.arcurus.organisationlist.repostory.MenuRepository;
import com.arcurus.organisationlist.service.RestaurantService;
import com.arcurus.organisationlist.wrappers.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class RestaurantController {
 private  final RestaurantService restaurantService;
    private final MenuRepository menuRepository;

    @PostMapping(value = {"addNewMenu"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Menus addNewMenu(@RequestPart("menus") Menus menus, @RequestPart("imageFile")MultipartFile[] files){
  try {
   Set<Images>images = restaurantService.uploadImage(files);
      menus.setImages(images);
      return menuRepository.save(menus);
  } catch (IOException e) {
   throw new RuntimeException(e);
  }
 }
     public Mono<ResponseEntity<UniversalResponse>>addRestaurant(RestaurantWrapper restaurant){
        return restaurantService.addRestaurant(restaurant)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
     }
   @PostMapping("/add/contact")
    public Mono<ResponseEntity<UniversalResponse>>addContacts(@RequestBody ContactWrapper contactWrapper,@RequestParam String name){
       return restaurantService.addContacts(contactWrapper, name)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    @PostMapping("/add/payment")
    public Mono<ResponseEntity<UniversalResponse>> addPayment(@RequestBody PaymentWrapper paymentWrapper,@RequestParam  String name){
        return restaurantService.addPayment(paymentWrapper,name)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    @GetMapping("/restaurants/food")
    public Mono<ResponseEntity<UniversalResponse>> getRestaurantsByFoodName(@RequestParam String name){
        return  restaurantService.getRestaurantsByFoodName(name)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    @GetMapping("/restaurant/price")
    public Mono<ResponseEntity<UniversalResponse>> getRestaurantsByFoodPrice(@RequestParam int price){
        return  restaurantService.getRestaurantsByFoodPrice(price)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    @GetMapping("/restaurants/rating")
    public Mono<ResponseEntity<UniversalResponse>>getRestaurantsByRating(@RequestParam long res){
        return  restaurantService.getRestaurantsByRating(res)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    @GetMapping("/restaurants/review")
    public Mono<ResponseEntity<UniversalResponse>>getRestaurantsByReviews(@RequestParam long restId){
        return restaurantService.getRestaurantsByReviews( restId)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    @PostMapping("/add/menu")
    public Mono<ResponseEntity<UniversalResponse>>  addMenu(@RequestBody MenuWrapper menuWrapper){
        return restaurantService. addMenu( menuWrapper)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    @PostMapping("/add/ingredient")
    public Mono<ResponseEntity<UniversalResponse>>  addIngredientsToMenu(@RequestParam String menuName,@RequestBody List<IngredientWrapper> ingredientWrappers){
        return restaurantService. addIngredientsToMenu(menuName,  ingredientWrappers)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    @PutMapping("/edit/menu/ingredient")
    public Mono<ResponseEntity<UniversalResponse>> editMenuIngredients(@RequestParam String menuName,@RequestBody List<IngredientWrapper>ingredients){
        return restaurantService.editMenuIngredients( menuName,ingredients)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    @PutMapping("/add/menu")
    public Mono<ResponseEntity<UniversalResponse>> editMenu(@RequestBody MenuWrapper menuWrapper){
        return restaurantService.editMenu(menuWrapper)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    @DeleteMapping("/add/menu")
    public Mono<ResponseEntity<UniversalResponse>> deleteMenu(@RequestBody String name){
        return restaurantService. deleteMenu( name)
                . map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    public Mono<ResponseEntity<UniversalResponse>>addMenuToRestaurants(String menuName,String restaurant){
        return restaurantService.addMenuToRestaurants( menuName, restaurant)
                . map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }


    public Mono<ResponseEntity<UniversalResponse>> readMenu( String restaurantName){
        return restaurantService.readMenu(restaurantName)
                . map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<UniversalResponse>> editContacts(ContactWrapper contactWrapper, String restaurantname){
        return restaurantService. editContacts( contactWrapper,  restaurantname)
                 . map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    public Mono<ResponseEntity<UniversalResponse>>   deleteContact(long contactId){
        return restaurantService.deleteContact( contactId)
                . map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    public Mono<ResponseEntity<UniversalResponse>>readContacts(long restaurantId){
        return restaurantService.readContacts(restaurantId)
               .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
    public Mono<ResponseEntity<UniversalResponse>> deleteIngredientFromMenu(long menuId,String ingredientName){
        return restaurantService. deleteIngredientFromMenu(menuId,ingredientName)
                .map(ResponseEntity::ok)
                .publishOn(Schedulers.boundedElastic());
    }
}


package com.arcurus.organisationlist.service.serviceImpl;

import com.arcurus.organisationlist.excepttions.*;
import com.arcurus.organisationlist.model.*;
import com.arcurus.organisationlist.repostory.*;
import com.arcurus.organisationlist.service.RestaurantService;
import com.arcurus.organisationlist.wrappers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RestaurantImpl implements RestaurantService {
    private  final RestaurantRepository restaurantRepository;
    private final IngredientRepository ingredientRepository;
    private  final ContactRepository contactRepository;
    private  final PaymentRepository paymentRepository;
    private final MenuRepository menuRepository;
    private final Path root = Paths.get("uploads");
    private final ImageRepository imageRepository;

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
           PaymentOption paymentOption =paymentRepository.findPaymentOptionByPaymentDetails(paymentWrapper.bankDetails());
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
    public Mono<UniversalResponse> getRestaurantsByRating(long res) {
        return Mono.fromCallable(() -> {
            List<Integer> restaurantRating = restaurantRepository.findRatingRestaurantId(res);
            long result = restaurantRating.stream().reduce(0, Integer::sum);
            return UniversalResponse.builder().data(restaurantRating).status(200).message("restaurantsating is" + result).build();
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
                    .foodPrice(menuWrapper.foodPrice())
                    .nutritionalInfo(menuWrapper.nutritionalInfo())
                    .build();
            menuRepository.save(menus1);
            return UniversalResponse.builder().status(200).message("menu created").data(menus1).build();
        }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UniversalResponse> addIngredientsToMenu(String menuName, List<IngredientWrapper>ingredientWrappers) {
       return Mono.fromCallable(()->{
            Menus menus = menuRepository.findMenusByFoodName(menuName).orElse(null);
            if (menus == null){
                throw new MenuException("menu with the given id does not exist");
            }
            Set<Ingredient>ingredients = new HashSet<>();
            for (IngredientWrapper ingredient:ingredientWrappers){
                Ingredient ingredients1 = ingredientRepository.findIngredientByName(ingredient.name());
                if (ingredients1 ==null){
                    Ingredient ingredient1 = Ingredient.builder().name(ingredient.name()).build();
                    ingredientRepository.save(ingredient1);
                }
                ingredients.add(ingredients1);
            }
            menus.setIngredient(ingredients);
            menuRepository.save(menus);
            return UniversalResponse.builder().status(200).message("ingredients added to menu").data(menus).build();
    }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UniversalResponse> editMenuIngredients(String name, List<IngredientWrapper> ingredientWrappers) {
        return Mono.fromCallable(()-> {
            Menus menu = menuRepository.findMenusByFoodName(name).orElseThrow(() -> new MenuException("Menu not found"));
            Set<Ingredient> ingredients = new HashSet<>();
            for (IngredientWrapper ingredientName : ingredientWrappers) {
                Ingredient ingredient = ingredientRepository.findIngredientByName(ingredientName.name());
                if (ingredient == null) {
                    ingredient = new Ingredient();
                    ingredient.setName(ingredientName.name());
                    ingredient = ingredientRepository.save(ingredient);
                }
                ingredients.add(ingredient);
            }
           menu.setIngredient(ingredients);
            menuRepository.save(menu);
        return UniversalResponse.builder().status(200).data(menu).message("message added").build();
   }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UniversalResponse> editMenu(MenuWrapper menuWrapper) {
        return Mono.fromCallable(()-> {
            Menus menus  = menuRepository.findMenusByFoodName(menuWrapper.foodName()).orElseThrow(()-> new MenuException("menu already exist"));
            menus.setFoodName(menuWrapper.foodName());
            menus.setFoodPrice(menuWrapper.foodPrice());
            menus.setNutritionalInfo(menuWrapper.nutritionalInfo());
            menus.setPreparationTime(menuWrapper.preparationTime());
            menuRepository.save(menus);
            return UniversalResponse.builder().status(200).message("menu edited").data(menus).build();

        }).publishOn(Schedulers.boundedElastic());
    }


    @Override
    public Mono<UniversalResponse> deleteMenu(String menuName) {
        return Mono.fromCallable(()->{
            Menus menu = menuRepository.findMenusByFoodName(menuName).orElseThrow(()-> new MenuException("Menu not found"));
            if (menu.getRestaurant()!=null){
                throw  new RestaurantException("this menu is already assigned to"+menu.getRestaurant()+" restaurants, we recommend editing  the menu to better suit your needs");
            }
            menuRepository.delete(menu);
            return UniversalResponse.builder().status(200).message("the menu has been deleted").data(menu).build();
        }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UniversalResponse> addMenuToRestaurants(String menuName, String restaurant) {
    return Mono.fromCallable(()->{;
        Restaurant restaurant1 =restaurantRepository.findRestaurantByNameIgnoreCase(restaurant).orElseThrow(()-> new RestaurantException("restaurant not found"));
        Menus menu = menuRepository.findMenusByFoodName(menuName).orElseThrow(()-> new MenuException("the  menu wasn't found"));
        List<Menus> menuList =restaurant1.getMenus();
        menuList.add(menu);
        restaurant1.setMenus(menuList);
        restaurantRepository.save(restaurant1);
       return UniversalResponse.builder().message("menu added to restaurant").data(restaurant1).status(200).build();
    }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UniversalResponse> readMenu( String restaurantName) {
        return Mono.fromCallable(()->{
            Restaurant restaurant = restaurantRepository.findRestaurantByNameIgnoreCase(restaurantName).orElseThrow(()-> new RestaurantException("retaurant with this name does not exist "));
            List<Menus>menuList = menuRepository.findByRestaurantId(restaurant.getId());
            return UniversalResponse.builder().status(200).message("here are the menu list for "+restaurantName+" restaurant").data(menuList).build();
        }).publishOn(Schedulers.boundedElastic());
    }


    @Override
    public Mono<UniversalResponse> editContacts(ContactWrapper contactWrapper, String restaurantname) {

        return Mono.fromCallable(()->{
            Restaurant restaurant = restaurantRepository.findRestaurantByNameIgnoreCase(restaurantname).orElseThrow(()->new RestaurantException("restaurant not found"));
            Contacts contacts = contactRepository.findContactsByName(contactWrapper.name()).orElse(null);
            if (contacts != null){
                throw new ContactException("the contact already exist");
            }
            Contacts contacts1 = Contacts.builder()
                    .name(contactWrapper.name())
                    .email(contactWrapper.email())
                    .phoneNumber(contactWrapper.phoneNumber())
                    .website(contactWrapper.website())
                    .build();
            restaurant.setContacts(contacts1);
           contactRepository.save(contacts1);
           return UniversalResponse.builder().status(200).message("contacts edited").data(contacts1).build();
        }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UniversalResponse> deleteContact(long restaurantId) {
        return Mono.fromCallable(()-> {
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()->new RestaurantException("restaurant not found"));
            contactRepository.delete(restaurant.getContacts());
            return UniversalResponse.builder().message("contact deleted").build();
        }).publishOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UniversalResponse> readContacts(long restaurantId) {
        return Mono.fromCallable(()-> {
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()->new RestaurantException("restaurant not found"));
            Contacts contacts = restaurant.getContacts();
            return UniversalResponse.builder().data(contacts).message("contact deleted").build();
        }).publishOn(Schedulers.boundedElastic());
    }



    @Override
    public Mono<UniversalResponse> deleteIngredientFromMenu(long menuId,String ingredientName) {
        return Mono.fromCallable(()-> {
            Menus menu  = menuRepository.findById(menuId).orElseThrow(()->new MenuException("menu not found"));
            Ingredient ingredient = ingredientRepository.findIngredientByName(ingredientName);
            Set<Ingredient>ingredients =menu.getIngredient();
            if (ingredients.contains(ingredient)) {
                ingredients.remove(ingredient);
                menu.setIngredient(ingredients);
                menuRepository.save(menu);
                return UniversalResponse.builder().data(menu).message("the ingredient have been remove from this menu").build();
            }
            throw  new IngredientException("ingredient was not found on the menu");
        }).publishOn(Schedulers.boundedElastic());
    }
    @Override
    public Set<Images> uploadImage(MultipartFile[] multipartFiles) throws IOException {
            Set<Images>images = new HashSet<>();
            for (MultipartFile file:multipartFiles){
                Images image = Images.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .bytes(file.getBytes()).build();
                images.add(image);
            }
            return  images;
    }


}

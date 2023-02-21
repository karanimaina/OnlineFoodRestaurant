package com.arcurus.organisationlist.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Menus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String foodName;
    @ManyToMany(mappedBy = "menus")
    private List<Restaurant> restaurant;

    @ManyToMany
    @JoinTable(name = "menu_ingredient",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredient= new ArrayList<>();
    @ManyToMany
    private List<Images> images;
    private String preparationTime;
    private String nutritionalInfo;
    private int foodPrice;
}

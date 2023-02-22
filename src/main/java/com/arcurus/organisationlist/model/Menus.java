package com.arcurus.organisationlist.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@SQLDelete(sql = "UPDATE menus SET soft_delete=true where id=?")
@Table(name = "menus")
@Where(clause = "soft_delete = false")
public class Menus extends BaseEntity {

    private String foodName;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "menu_ingredient",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredient= new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_images",
    joinColumns = {
            @JoinColumn(name = "product_id")},inverseJoinColumns = {
            @JoinColumn(name = "image_id")
    }
    )
    private Set<Images> images;
    private String preparationTime;
    private String nutritionalInfo;
    private int foodPrice;
}

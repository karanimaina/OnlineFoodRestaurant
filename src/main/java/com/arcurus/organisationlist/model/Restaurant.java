package com.arcurus.organisationlist.model;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
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
public  class Restaurant  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private String location;
    @ManyToMany
    @JoinTable(name = "restaurant_menu",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<Menus> menus = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "restaurant_review",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id"))
    private Set<Review> reviews = new HashSet<>();
    @OneToOne
    private Contacts contacts;
    @OneToOne
    private PaymentOption paymentOption;
    private int shippingCost;
}
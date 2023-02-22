package com.arcurus.organisationlist.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@Table(name = "restaurant")
@SQLDelete(sql="UPDATE restaurant SET soft_delete=true where id=?")
@Where(clause = "soft_delete = false")
@Builder
public  class Restaurant extends BaseEntity {
    @Column(unique = true)
    private String name;
    private String location;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Menus> menus;
    @ManyToMany //manytoone
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
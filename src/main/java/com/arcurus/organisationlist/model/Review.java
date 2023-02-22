package com.arcurus.organisationlist.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "review")
@SQLDelete(sql="UPDATE review SET soft_delete=true where id=?")
@Where(clause = "soft_delete = false")
public class Review extends BaseEntity {
    private  String review;
    private int rating;
    @ManyToMany(mappedBy = "reviews")//onetomany
    private Set<Restaurant> restaurant= new HashSet<>();
}

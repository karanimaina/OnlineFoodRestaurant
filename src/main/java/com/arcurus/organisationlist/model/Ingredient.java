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
@Table(name = "ingredient")
@SQLDelete(sql="UPDATE image SET soft_delete=true where id=?")
@Where(clause = "soft_delete = false")
@Builder
public class Ingredient  extends BaseEntity {
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "ingredient")
    private Set<Menus> menus = new HashSet<>();
}

package com.arcurus.organisationlist.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "contacts")
@SQLDelete(sql="UPDATE contacts SET soft_delete=true where id=?")
@Where(clause = "soft_delete = false")
@Setter
public class Contacts extends BaseEntity{
    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    @Nullable
    private String website;
}

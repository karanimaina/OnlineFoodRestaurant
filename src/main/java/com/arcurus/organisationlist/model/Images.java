package com.arcurus.organisationlist.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "image")
@SQLDelete(sql="UPDATE image SET soft_delete=true where id=?")
@Where(clause = "soft_delete = false")
public class Images  extends BaseEntity{
    private String name;
    private String type;
    @Column(length = 500000)
    private byte[] bytes;
}

package com.arcurus.organisationlist.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "payment")
@SQLDelete(sql="UPDATE payment SET soft_delete=true where id=?")
@Where(clause = "soft_delete = false")
@Builder
public class PaymentOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)@Size(min = 12,message = "the payment details must have a minimum of 12 characters")
    private String  paymentDetails;
}
package com.arcurus.organisationlist.wrappers;

import com.arcurus.organisationlist.model.Contacts;
import com.arcurus.organisationlist.model.Menus;
import com.arcurus.organisationlist.model.PaymentOption;
import com.arcurus.organisationlist.model.Review;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

public record RestaurantWrapper (

        String name,

        String location,

        Contacts contactId,

        PaymentOption paymentOptionId,

        int shippingCost
)
{
}

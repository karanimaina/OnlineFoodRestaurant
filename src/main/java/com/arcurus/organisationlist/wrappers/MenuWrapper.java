package com.arcurus.organisationlist.wrappers;

import com.arcurus.organisationlist.model.Images;
import com.arcurus.organisationlist.model.Ingredient;
import com.arcurus.organisationlist.model.Restaurant;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

public record MenuWrapper(
         String foodName,
         String preparationTime,
         String nutritionalInfo,
        int foodPrice
) {
}

package com.arcurus.organisationlist.repostory;

import com.arcurus.organisationlist.wrappers.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food,Long> {
}

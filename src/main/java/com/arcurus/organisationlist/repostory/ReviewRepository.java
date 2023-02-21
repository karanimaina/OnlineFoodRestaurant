package com.arcurus.organisationlist.repostory;

import com.arcurus.organisationlist.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}

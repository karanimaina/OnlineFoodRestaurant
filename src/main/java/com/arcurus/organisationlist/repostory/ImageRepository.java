package com.arcurus.organisationlist.repostory;

import com.arcurus.organisationlist.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Images,Long> {
}

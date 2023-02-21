package com.arcurus.organisationlist.repostory;

import com.arcurus.organisationlist.model.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contacts,Long>{
    Optional<Contacts> findContactsByName(String name);
}

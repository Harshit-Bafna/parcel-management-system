package com.parcel_management_system.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcel_management_system.app.entity.OfficerProfile;

@Repository
public interface OfficerRepository extends JpaRepository<OfficerProfile, Long> {

    Optional<OfficerProfile> findByUserId(Long id);
    
}

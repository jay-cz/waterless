package com.jay.repository;

import com.jay.domain.HouseHold;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HouseHold entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HouseHoldRepository extends JpaRepository<HouseHold, Long> {

}

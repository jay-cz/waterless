package com.jay.repository;

import com.jay.domain.MeasurePoint;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MeasurePoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeasurePointRepository extends JpaRepository<MeasurePoint, Long> {

}

package com.jay.repository;

import com.jay.domain.MeasurePoint;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MeasurePoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeasurePointRepository extends JpaRepository<MeasurePoint, Long> {

    /**
     * Gets data which are valid in the selected time interval
     * @param start
     * @param end
     * @return
     */

    //First condition - interval overlaps OR
    //Second - start is in the selected interval OR
    //Third - end is in the selected interval
    @Query("select mp from MeasurePoint mp where (mp.startTm >= :start AND mp.endTm <= :end) "
            + " OR (mp.startTm >= :start AND mp.startTm <= :end) "
            + " OR (mp.endTm >= :start AND mp.endTm <= :end)")
    List<MeasurePoint> findAll(@Param("start") LocalDate start,@Param("end") LocalDate end);
}

package com.jay.service;

import com.jay.service.dto.HouseHoldDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing HouseHold.
 */
public interface HouseHoldService {

    /**
     * Save a houseHold.
     *
     * @param houseHoldDTO the entity to save
     * @return the persisted entity
     */
    HouseHoldDTO save(HouseHoldDTO houseHoldDTO);

    /**
     *  Get all the houseHolds.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HouseHoldDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" houseHold.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HouseHoldDTO findOne(Long id);

    /**
     *  Delete the "id" houseHold.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

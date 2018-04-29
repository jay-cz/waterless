package com.jay.service;

import com.jay.service.dto.MeasureDataDTO;
import com.jay.service.dto.MeasurePointDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MeasurePoint.
 */
public interface MeasurePointService {

    /**
     * Save a measurePoint.
     *
     * @param measurePointDTO the entity to save
     * @return the persisted entity
     */
    MeasurePointDTO save(MeasurePointDTO measurePointDTO);

    /**
     *  Get all the measurePoints.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeasurePointDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" measurePoint.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MeasurePointDTO findOne(Long id);

    /**
     *  Delete the "id" measurePoint.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Simply get all measuredata
     * @return
     */
    List<MeasureDataDTO> findAllMeasureData();

    /**
     * Finds all measure data within selected timerange
     * @return
     */
    List<MeasureDataDTO> findAllMeasureData(LocalDate start, LocalDate end);
}

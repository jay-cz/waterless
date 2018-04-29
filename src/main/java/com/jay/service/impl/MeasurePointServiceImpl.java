package com.jay.service.impl;

import com.jay.service.MeasurePointService;
import com.jay.domain.MeasurePoint;
import com.jay.repository.MeasurePointRepository;
import com.jay.service.dto.MeasureDataDTO;
import com.jay.service.dto.MeasurePointDTO;
import com.jay.service.mapper.MeasurePointMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MeasurePoint.
 */
@Service
@Transactional
public class MeasurePointServiceImpl implements MeasurePointService{

    private final Logger log = LoggerFactory.getLogger(MeasurePointServiceImpl.class);

    private final MeasurePointRepository measurePointRepository;

    private final MeasurePointMapper measurePointMapper;
    public MeasurePointServiceImpl(MeasurePointRepository measurePointRepository, MeasurePointMapper measurePointMapper) {
        this.measurePointRepository = measurePointRepository;
        this.measurePointMapper = measurePointMapper;
    }

    /**
     * Save a measurePoint.
     *
     * @param measurePointDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MeasurePointDTO save(MeasurePointDTO measurePointDTO) {
        log.debug("Request to save MeasurePoint : {}", measurePointDTO);
        MeasurePoint measurePoint = measurePointMapper.toEntity(measurePointDTO);
        measurePoint = measurePointRepository.save(measurePoint);
        return measurePointMapper.toDto(measurePoint);
    }

    /**
     *  Get all the measurePoints.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeasurePointDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeasurePoints");
        return measurePointRepository.findAll(pageable)
            .map(measurePointMapper::toDto);
    }

    /**
     *  Get one measurePoint by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MeasurePointDTO findOne(Long id) {
        log.debug("Request to get MeasurePoint : {}", id);
        MeasurePoint measurePoint = measurePointRepository.findOne(id);
        return measurePointMapper.toDto(measurePoint);
    }

    /**
     *  Delete the  measurePoint by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeasurePoint : {}", id);
        measurePointRepository.delete(id);
    }

    @Override
    public List<MeasureDataDTO> findAllMeasureData() {

        List<MeasurePoint> measurePoints = measurePointRepository.findAll();
        List<MeasureDataDTO> measureData = this.tranformMeasurePointIntoMeasureData(measurePoints);

        return measureData;
    }

    @Override
    public List<MeasureDataDTO> findAllMeasureData(LocalDate start, LocalDate end) {

        //Lets find all measure points
        List<MeasurePoint> measurePoints = measurePointRepository.findAll(start, end);
        List<MeasureDataDTO> measureData = this.tranformMeasurePointIntoMeasureData(measurePoints);
        return measureData;
    }

    private List<MeasureDataDTO> tranformMeasurePointIntoMeasureData(List<MeasurePoint> measurePoints){
        List<MeasureDataDTO> measureData = new ArrayList<>();
        //Tranform measurePoints to DTO
        for (MeasurePoint point: measurePoints) {
            MeasureDataDTO measureDataDTO = new MeasureDataDTO(point);
            measureData.add(measureDataDTO);
        }

        return measureData;
    }
}

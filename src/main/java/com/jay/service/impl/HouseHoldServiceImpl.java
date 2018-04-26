package com.jay.service.impl;

import com.jay.service.HouseHoldService;
import com.jay.domain.HouseHold;
import com.jay.repository.HouseHoldRepository;
import com.jay.service.dto.HouseHoldDTO;
import com.jay.service.mapper.HouseHoldMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing HouseHold.
 */
@Service
@Transactional
public class HouseHoldServiceImpl implements HouseHoldService{

    private final Logger log = LoggerFactory.getLogger(HouseHoldServiceImpl.class);

    private final HouseHoldRepository houseHoldRepository;

    private final HouseHoldMapper houseHoldMapper;
    public HouseHoldServiceImpl(HouseHoldRepository houseHoldRepository, HouseHoldMapper houseHoldMapper) {
        this.houseHoldRepository = houseHoldRepository;
        this.houseHoldMapper = houseHoldMapper;
    }

    /**
     * Save a houseHold.
     *
     * @param houseHoldDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HouseHoldDTO save(HouseHoldDTO houseHoldDTO) {
        log.debug("Request to save HouseHold : {}", houseHoldDTO);
        HouseHold houseHold = houseHoldMapper.toEntity(houseHoldDTO);
        houseHold = houseHoldRepository.save(houseHold);
        return houseHoldMapper.toDto(houseHold);
    }

    /**
     *  Get all the houseHolds.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HouseHoldDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HouseHolds");
        return houseHoldRepository.findAll(pageable)
            .map(houseHoldMapper::toDto);
    }

    /**
     *  Get one houseHold by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HouseHoldDTO findOne(Long id) {
        log.debug("Request to get HouseHold : {}", id);
        HouseHold houseHold = houseHoldRepository.findOne(id);
        return houseHoldMapper.toDto(houseHold);
    }

    /**
     *  Delete the  houseHold by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HouseHold : {}", id);
        houseHoldRepository.delete(id);
    }
}

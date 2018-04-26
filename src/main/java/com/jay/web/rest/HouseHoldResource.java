package com.jay.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jay.service.HouseHoldService;
import com.jay.web.rest.util.HeaderUtil;
import com.jay.web.rest.util.PaginationUtil;
import com.jay.service.dto.HouseHoldDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HouseHold.
 */
@RestController
@RequestMapping("/api")
public class HouseHoldResource {

    private final Logger log = LoggerFactory.getLogger(HouseHoldResource.class);

    private static final String ENTITY_NAME = "houseHold";

    private final HouseHoldService houseHoldService;

    public HouseHoldResource(HouseHoldService houseHoldService) {
        this.houseHoldService = houseHoldService;
    }

    /**
     * POST  /house-holds : Create a new houseHold.
     *
     * @param houseHoldDTO the houseHoldDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new houseHoldDTO, or with status 400 (Bad Request) if the houseHold has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/house-holds")
    @Timed
    public ResponseEntity<HouseHoldDTO> createHouseHold(@RequestBody HouseHoldDTO houseHoldDTO) throws URISyntaxException {
        log.debug("REST request to save HouseHold : {}", houseHoldDTO);
        if (houseHoldDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new houseHold cannot already have an ID")).body(null);
        }
        HouseHoldDTO result = houseHoldService.save(houseHoldDTO);
        return ResponseEntity.created(new URI("/api/house-holds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /house-holds : Updates an existing houseHold.
     *
     * @param houseHoldDTO the houseHoldDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated houseHoldDTO,
     * or with status 400 (Bad Request) if the houseHoldDTO is not valid,
     * or with status 500 (Internal Server Error) if the houseHoldDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/house-holds")
    @Timed
    public ResponseEntity<HouseHoldDTO> updateHouseHold(@RequestBody HouseHoldDTO houseHoldDTO) throws URISyntaxException {
        log.debug("REST request to update HouseHold : {}", houseHoldDTO);
        if (houseHoldDTO.getId() == null) {
            return createHouseHold(houseHoldDTO);
        }
        HouseHoldDTO result = houseHoldService.save(houseHoldDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, houseHoldDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /house-holds : get all the houseHolds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of houseHolds in body
     */
    @GetMapping("/house-holds")
    @Timed
    public ResponseEntity<List<HouseHoldDTO>> getAllHouseHolds(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HouseHolds");
        Page<HouseHoldDTO> page = houseHoldService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/house-holds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /house-holds/:id : get the "id" houseHold.
     *
     * @param id the id of the houseHoldDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the houseHoldDTO, or with status 404 (Not Found)
     */
    @GetMapping("/house-holds/{id}")
    @Timed
    public ResponseEntity<HouseHoldDTO> getHouseHold(@PathVariable Long id) {
        log.debug("REST request to get HouseHold : {}", id);
        HouseHoldDTO houseHoldDTO = houseHoldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(houseHoldDTO));
    }

    /**
     * DELETE  /house-holds/:id : delete the "id" houseHold.
     *
     * @param id the id of the houseHoldDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/house-holds/{id}")
    @Timed
    public ResponseEntity<Void> deleteHouseHold(@PathVariable Long id) {
        log.debug("REST request to delete HouseHold : {}", id);
        houseHoldService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

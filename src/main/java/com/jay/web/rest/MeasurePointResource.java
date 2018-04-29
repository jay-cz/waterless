package com.jay.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jay.service.MeasurePointService;
import com.jay.service.dto.MeasureDataDTO;
import com.jay.web.rest.util.HeaderUtil;
import com.jay.web.rest.util.PaginationUtil;
import com.jay.service.dto.MeasurePointDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import java.time.LocalDate;
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
 * REST controller for managing MeasurePoint.
 */
@RestController
@RequestMapping("/api")
public class MeasurePointResource {

    private final Logger log = LoggerFactory.getLogger(MeasurePointResource.class);

    private static final String ENTITY_NAME = "measurePoint";

    private final MeasurePointService measurePointService;

    public MeasurePointResource(MeasurePointService measurePointService) {
        this.measurePointService = measurePointService;
    }

    /**
     * POST  /measure-points : Create a new measurePoint.
     *
     * @param measurePointDTO the measurePointDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new measurePointDTO, or with status 400 (Bad Request) if the measurePoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/measure-points")
    @Timed
    public ResponseEntity<MeasurePointDTO> createMeasurePoint(@RequestBody MeasurePointDTO measurePointDTO) throws URISyntaxException {
        log.debug("REST request to save MeasurePoint : {}", measurePointDTO);
        if (measurePointDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new measurePoint cannot already have an ID")).body(null);
        }
        MeasurePointDTO result = measurePointService.save(measurePointDTO);
        return ResponseEntity.created(new URI("/api/measure-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /measure-points : Updates an existing measurePoint.
     *
     * @param measurePointDTO the measurePointDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated measurePointDTO,
     * or with status 400 (Bad Request) if the measurePointDTO is not valid,
     * or with status 500 (Internal Server Error) if the measurePointDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/measure-points")
    @Timed
    public ResponseEntity<MeasurePointDTO> updateMeasurePoint(@RequestBody MeasurePointDTO measurePointDTO) throws URISyntaxException {
        log.debug("REST request to update MeasurePoint : {}", measurePointDTO);
        if (measurePointDTO.getId() == null) {
            return createMeasurePoint(measurePointDTO);
        }
        MeasurePointDTO result = measurePointService.save(measurePointDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, measurePointDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /measure-points : get all the measurePoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of measurePoints in body
     */
    @GetMapping("/measure-point-data/getAll")
    @Timed
    public ResponseEntity<List<MeasureDataDTO>> getAllMeasurePoints() {
        log.debug("REST request to get a page of MeasurePoints");
        List<MeasureDataDTO> measureData = measurePointService.findAllMeasureData();
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/measure-points");
        return new ResponseEntity<>(measureData, HttpStatus.OK);
    }



    /**
     * GET  /measure-points : get all the measurePoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of measurePoints in body
     */
    @GetMapping("/measure-point-data")
    @Timed
    public ResponseEntity<List<MeasureDataDTO>> getAllMeasurePointsInTimeRange(@RequestParam LocalDate start,@RequestParam LocalDate end ) {
        log.debug("REST request to get a page of MeasurePoints");
        List<MeasureDataDTO> measureData = measurePointService.findAllMeasureData(start, end);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/measure-points");
        return new ResponseEntity<>(measureData, HttpStatus.OK);
    }

    /**
     * GET  /measure-points : get all the measurePoints.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of measurePoints in body
     */
    @GetMapping("/measure-points")
    @Timed
    public ResponseEntity<List<MeasurePointDTO>> getAllMeasurePoints(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MeasurePoints");
        Page<MeasurePointDTO> page = measurePointService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/measure-points");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /measure-points/:id : get the "id" measurePoint.
     *
     * @param id the id of the measurePointDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the measurePointDTO, or with status 404 (Not Found)
     */
    @GetMapping("/measure-points/{id}")
    @Timed
    public ResponseEntity<MeasurePointDTO> getMeasurePoint(@PathVariable Long id) {
        log.debug("REST request to get MeasurePoint : {}", id);
        MeasurePointDTO measurePointDTO = measurePointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(measurePointDTO));
    }

    /**
     * DELETE  /measure-points/:id : delete the "id" measurePoint.
     *
     * @param id the id of the measurePointDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/measure-points/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeasurePoint(@PathVariable Long id) {
        log.debug("REST request to delete MeasurePoint : {}", id);
        measurePointService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

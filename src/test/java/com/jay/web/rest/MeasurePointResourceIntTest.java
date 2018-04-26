package com.jay.web.rest;

import com.jay.WaterlessApp;

import com.jay.domain.MeasurePoint;
import com.jay.repository.MeasurePointRepository;
import com.jay.service.MeasurePointService;
import com.jay.service.dto.MeasurePointDTO;
import com.jay.service.mapper.MeasurePointMapper;
import com.jay.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MeasurePointResource REST controller.
 *
 * @see MeasurePointResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WaterlessApp.class)
public class MeasurePointResourceIntTest {

    private static final LocalDate DEFAULT_START_TM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_TM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_TM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_TM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private MeasurePointRepository measurePointRepository;

    @Autowired
    private MeasurePointMapper measurePointMapper;

    @Autowired
    private MeasurePointService measurePointService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeasurePointMockMvc;

    private MeasurePoint measurePoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeasurePointResource measurePointResource = new MeasurePointResource(measurePointService);
        this.restMeasurePointMockMvc = MockMvcBuilders.standaloneSetup(measurePointResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeasurePoint createEntity(EntityManager em) {
        MeasurePoint measurePoint = new MeasurePoint()
            .startTm(DEFAULT_START_TM)
            .endTm(DEFAULT_END_TM)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .value(DEFAULT_VALUE);
        return measurePoint;
    }

    @Before
    public void initTest() {
        measurePoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeasurePoint() throws Exception {
        int databaseSizeBeforeCreate = measurePointRepository.findAll().size();

        // Create the MeasurePoint
        MeasurePointDTO measurePointDTO = measurePointMapper.toDto(measurePoint);
        restMeasurePointMockMvc.perform(post("/api/measure-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurePointDTO)))
            .andExpect(status().isCreated());

        // Validate the MeasurePoint in the database
        List<MeasurePoint> measurePointList = measurePointRepository.findAll();
        assertThat(measurePointList).hasSize(databaseSizeBeforeCreate + 1);
        MeasurePoint testMeasurePoint = measurePointList.get(measurePointList.size() - 1);
        assertThat(testMeasurePoint.getStartTm()).isEqualTo(DEFAULT_START_TM);
        assertThat(testMeasurePoint.getEndTm()).isEqualTo(DEFAULT_END_TM);
        assertThat(testMeasurePoint.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testMeasurePoint.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createMeasurePointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = measurePointRepository.findAll().size();

        // Create the MeasurePoint with an existing ID
        measurePoint.setId(1L);
        MeasurePointDTO measurePointDTO = measurePointMapper.toDto(measurePoint);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeasurePointMockMvc.perform(post("/api/measure-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurePointDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MeasurePoint> measurePointList = measurePointRepository.findAll();
        assertThat(measurePointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMeasurePoints() throws Exception {
        // Initialize the database
        measurePointRepository.saveAndFlush(measurePoint);

        // Get all the measurePointList
        restMeasurePointMockMvc.perform(get("/api/measure-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(measurePoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTm").value(hasItem(DEFAULT_START_TM.toString())))
            .andExpect(jsonPath("$.[*].endTm").value(hasItem(DEFAULT_END_TM.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getMeasurePoint() throws Exception {
        // Initialize the database
        measurePointRepository.saveAndFlush(measurePoint);

        // Get the measurePoint
        restMeasurePointMockMvc.perform(get("/api/measure-points/{id}", measurePoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(measurePoint.getId().intValue()))
            .andExpect(jsonPath("$.startTm").value(DEFAULT_START_TM.toString()))
            .andExpect(jsonPath("$.endTm").value(DEFAULT_END_TM.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMeasurePoint() throws Exception {
        // Get the measurePoint
        restMeasurePointMockMvc.perform(get("/api/measure-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeasurePoint() throws Exception {
        // Initialize the database
        measurePointRepository.saveAndFlush(measurePoint);
        int databaseSizeBeforeUpdate = measurePointRepository.findAll().size();

        // Update the measurePoint
        MeasurePoint updatedMeasurePoint = measurePointRepository.findOne(measurePoint.getId());
        updatedMeasurePoint
            .startTm(UPDATED_START_TM)
            .endTm(UPDATED_END_TM)
            .ipAddress(UPDATED_IP_ADDRESS)
            .value(UPDATED_VALUE);
        MeasurePointDTO measurePointDTO = measurePointMapper.toDto(updatedMeasurePoint);

        restMeasurePointMockMvc.perform(put("/api/measure-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurePointDTO)))
            .andExpect(status().isOk());

        // Validate the MeasurePoint in the database
        List<MeasurePoint> measurePointList = measurePointRepository.findAll();
        assertThat(measurePointList).hasSize(databaseSizeBeforeUpdate);
        MeasurePoint testMeasurePoint = measurePointList.get(measurePointList.size() - 1);
        assertThat(testMeasurePoint.getStartTm()).isEqualTo(UPDATED_START_TM);
        assertThat(testMeasurePoint.getEndTm()).isEqualTo(UPDATED_END_TM);
        assertThat(testMeasurePoint.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testMeasurePoint.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingMeasurePoint() throws Exception {
        int databaseSizeBeforeUpdate = measurePointRepository.findAll().size();

        // Create the MeasurePoint
        MeasurePointDTO measurePointDTO = measurePointMapper.toDto(measurePoint);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeasurePointMockMvc.perform(put("/api/measure-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurePointDTO)))
            .andExpect(status().isCreated());

        // Validate the MeasurePoint in the database
        List<MeasurePoint> measurePointList = measurePointRepository.findAll();
        assertThat(measurePointList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeasurePoint() throws Exception {
        // Initialize the database
        measurePointRepository.saveAndFlush(measurePoint);
        int databaseSizeBeforeDelete = measurePointRepository.findAll().size();

        // Get the measurePoint
        restMeasurePointMockMvc.perform(delete("/api/measure-points/{id}", measurePoint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MeasurePoint> measurePointList = measurePointRepository.findAll();
        assertThat(measurePointList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeasurePoint.class);
        MeasurePoint measurePoint1 = new MeasurePoint();
        measurePoint1.setId(1L);
        MeasurePoint measurePoint2 = new MeasurePoint();
        measurePoint2.setId(measurePoint1.getId());
        assertThat(measurePoint1).isEqualTo(measurePoint2);
        measurePoint2.setId(2L);
        assertThat(measurePoint1).isNotEqualTo(measurePoint2);
        measurePoint1.setId(null);
        assertThat(measurePoint1).isNotEqualTo(measurePoint2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeasurePointDTO.class);
        MeasurePointDTO measurePointDTO1 = new MeasurePointDTO();
        measurePointDTO1.setId(1L);
        MeasurePointDTO measurePointDTO2 = new MeasurePointDTO();
        assertThat(measurePointDTO1).isNotEqualTo(measurePointDTO2);
        measurePointDTO2.setId(measurePointDTO1.getId());
        assertThat(measurePointDTO1).isEqualTo(measurePointDTO2);
        measurePointDTO2.setId(2L);
        assertThat(measurePointDTO1).isNotEqualTo(measurePointDTO2);
        measurePointDTO1.setId(null);
        assertThat(measurePointDTO1).isNotEqualTo(measurePointDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(measurePointMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(measurePointMapper.fromId(null)).isNull();
    }
}

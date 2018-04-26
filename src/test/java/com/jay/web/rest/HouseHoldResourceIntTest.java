package com.jay.web.rest;

import com.jay.WaterlessApp;

import com.jay.domain.HouseHold;
import com.jay.repository.HouseHoldRepository;
import com.jay.service.HouseHoldService;
import com.jay.service.dto.HouseHoldDTO;
import com.jay.service.mapper.HouseHoldMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HouseHoldResource REST controller.
 *
 * @see HouseHoldResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WaterlessApp.class)
public class HouseHoldResourceIntTest {

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_FLAT_NUMBER = 1;
    private static final Integer UPDATED_FLAT_NUMBER = 2;

    private static final Integer DEFAULT_NUMBER_OF_PEOPLE = 1;
    private static final Integer UPDATED_NUMBER_OF_PEOPLE = 2;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    @Autowired
    private HouseHoldRepository houseHoldRepository;

    @Autowired
    private HouseHoldMapper houseHoldMapper;

    @Autowired
    private HouseHoldService houseHoldService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHouseHoldMockMvc;

    private HouseHold houseHold;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HouseHoldResource houseHoldResource = new HouseHoldResource(houseHoldService);
        this.restHouseHoldMockMvc = MockMvcBuilders.standaloneSetup(houseHoldResource)
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
    public static HouseHold createEntity(EntityManager em) {
        HouseHold houseHold = new HouseHold()
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .houseNumber(DEFAULT_HOUSE_NUMBER)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .flatNumber(DEFAULT_FLAT_NUMBER)
            .numberOfPeople(DEFAULT_NUMBER_OF_PEOPLE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return houseHold;
    }

    @Before
    public void initTest() {
        houseHold = createEntity(em);
    }

    @Test
    @Transactional
    public void createHouseHold() throws Exception {
        int databaseSizeBeforeCreate = houseHoldRepository.findAll().size();

        // Create the HouseHold
        HouseHoldDTO houseHoldDTO = houseHoldMapper.toDto(houseHold);
        restHouseHoldMockMvc.perform(post("/api/house-holds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseHoldDTO)))
            .andExpect(status().isCreated());

        // Validate the HouseHold in the database
        List<HouseHold> houseHoldList = houseHoldRepository.findAll();
        assertThat(houseHoldList).hasSize(databaseSizeBeforeCreate + 1);
        HouseHold testHouseHold = houseHoldList.get(houseHoldList.size() - 1);
        assertThat(testHouseHold.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testHouseHold.getHouseNumber()).isEqualTo(DEFAULT_HOUSE_NUMBER);
        assertThat(testHouseHold.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testHouseHold.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testHouseHold.getFlatNumber()).isEqualTo(DEFAULT_FLAT_NUMBER);
        assertThat(testHouseHold.getNumberOfPeople()).isEqualTo(DEFAULT_NUMBER_OF_PEOPLE);
        assertThat(testHouseHold.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testHouseHold.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createHouseHoldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = houseHoldRepository.findAll().size();

        // Create the HouseHold with an existing ID
        houseHold.setId(1L);
        HouseHoldDTO houseHoldDTO = houseHoldMapper.toDto(houseHold);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHouseHoldMockMvc.perform(post("/api/house-holds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseHoldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HouseHold> houseHoldList = houseHoldRepository.findAll();
        assertThat(houseHoldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHouseHolds() throws Exception {
        // Initialize the database
        houseHoldRepository.saveAndFlush(houseHold);

        // Get all the houseHoldList
        restHouseHoldMockMvc.perform(get("/api/house-holds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(houseHold.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].flatNumber").value(hasItem(DEFAULT_FLAT_NUMBER)))
            .andExpect(jsonPath("$.[*].numberOfPeople").value(hasItem(DEFAULT_NUMBER_OF_PEOPLE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }

    @Test
    @Transactional
    public void getHouseHold() throws Exception {
        // Initialize the database
        houseHoldRepository.saveAndFlush(houseHold);

        // Get the houseHold
        restHouseHoldMockMvc.perform(get("/api/house-holds/{id}", houseHold.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(houseHold.getId().intValue()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.flatNumber").value(DEFAULT_FLAT_NUMBER))
            .andExpect(jsonPath("$.numberOfPeople").value(DEFAULT_NUMBER_OF_PEOPLE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHouseHold() throws Exception {
        // Get the houseHold
        restHouseHoldMockMvc.perform(get("/api/house-holds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHouseHold() throws Exception {
        // Initialize the database
        houseHoldRepository.saveAndFlush(houseHold);
        int databaseSizeBeforeUpdate = houseHoldRepository.findAll().size();

        // Update the houseHold
        HouseHold updatedHouseHold = houseHoldRepository.findOne(houseHold.getId());
        updatedHouseHold
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .flatNumber(UPDATED_FLAT_NUMBER)
            .numberOfPeople(UPDATED_NUMBER_OF_PEOPLE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        HouseHoldDTO houseHoldDTO = houseHoldMapper.toDto(updatedHouseHold);

        restHouseHoldMockMvc.perform(put("/api/house-holds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseHoldDTO)))
            .andExpect(status().isOk());

        // Validate the HouseHold in the database
        List<HouseHold> houseHoldList = houseHoldRepository.findAll();
        assertThat(houseHoldList).hasSize(databaseSizeBeforeUpdate);
        HouseHold testHouseHold = houseHoldList.get(houseHoldList.size() - 1);
        assertThat(testHouseHold.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testHouseHold.getHouseNumber()).isEqualTo(UPDATED_HOUSE_NUMBER);
        assertThat(testHouseHold.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testHouseHold.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testHouseHold.getFlatNumber()).isEqualTo(UPDATED_FLAT_NUMBER);
        assertThat(testHouseHold.getNumberOfPeople()).isEqualTo(UPDATED_NUMBER_OF_PEOPLE);
        assertThat(testHouseHold.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testHouseHold.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingHouseHold() throws Exception {
        int databaseSizeBeforeUpdate = houseHoldRepository.findAll().size();

        // Create the HouseHold
        HouseHoldDTO houseHoldDTO = houseHoldMapper.toDto(houseHold);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHouseHoldMockMvc.perform(put("/api/house-holds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseHoldDTO)))
            .andExpect(status().isCreated());

        // Validate the HouseHold in the database
        List<HouseHold> houseHoldList = houseHoldRepository.findAll();
        assertThat(houseHoldList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHouseHold() throws Exception {
        // Initialize the database
        houseHoldRepository.saveAndFlush(houseHold);
        int databaseSizeBeforeDelete = houseHoldRepository.findAll().size();

        // Get the houseHold
        restHouseHoldMockMvc.perform(delete("/api/house-holds/{id}", houseHold.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HouseHold> houseHoldList = houseHoldRepository.findAll();
        assertThat(houseHoldList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseHold.class);
        HouseHold houseHold1 = new HouseHold();
        houseHold1.setId(1L);
        HouseHold houseHold2 = new HouseHold();
        houseHold2.setId(houseHold1.getId());
        assertThat(houseHold1).isEqualTo(houseHold2);
        houseHold2.setId(2L);
        assertThat(houseHold1).isNotEqualTo(houseHold2);
        houseHold1.setId(null);
        assertThat(houseHold1).isNotEqualTo(houseHold2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseHoldDTO.class);
        HouseHoldDTO houseHoldDTO1 = new HouseHoldDTO();
        houseHoldDTO1.setId(1L);
        HouseHoldDTO houseHoldDTO2 = new HouseHoldDTO();
        assertThat(houseHoldDTO1).isNotEqualTo(houseHoldDTO2);
        houseHoldDTO2.setId(houseHoldDTO1.getId());
        assertThat(houseHoldDTO1).isEqualTo(houseHoldDTO2);
        houseHoldDTO2.setId(2L);
        assertThat(houseHoldDTO1).isNotEqualTo(houseHoldDTO2);
        houseHoldDTO1.setId(null);
        assertThat(houseHoldDTO1).isNotEqualTo(houseHoldDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(houseHoldMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(houseHoldMapper.fromId(null)).isNull();
    }
}

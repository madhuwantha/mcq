package com.equitem.soft.mcq.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.equitem.soft.mcq.IntegrationTest;
import com.equitem.soft.mcq.domain.McqPapper;
import com.equitem.soft.mcq.repository.McqPapperRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link McqPapperResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class McqPapperResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIME_IN_MIN = 1;
    private static final Integer UPDATED_TIME_IN_MIN = 2;

    private static final String ENTITY_API_URL = "/api/mcq-pappers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private McqPapperRepository mcqPapperRepository;

    @Mock
    private McqPapperRepository mcqPapperRepositoryMock;

    @Autowired
    private MockMvc restMcqPapperMockMvc;

    private McqPapper mcqPapper;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static McqPapper createEntity() {
        McqPapper mcqPapper = new McqPapper().title(DEFAULT_TITLE).timeInMin(DEFAULT_TIME_IN_MIN);
        return mcqPapper;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static McqPapper createUpdatedEntity() {
        McqPapper mcqPapper = new McqPapper().title(UPDATED_TITLE).timeInMin(UPDATED_TIME_IN_MIN);
        return mcqPapper;
    }

    @BeforeEach
    public void initTest() {
        mcqPapperRepository.deleteAll();
        mcqPapper = createEntity();
    }

    @Test
    void createMcqPapper() throws Exception {
        int databaseSizeBeforeCreate = mcqPapperRepository.findAll().size();
        // Create the McqPapper
        restMcqPapperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mcqPapper)))
            .andExpect(status().isCreated());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeCreate + 1);
        McqPapper testMcqPapper = mcqPapperList.get(mcqPapperList.size() - 1);
        assertThat(testMcqPapper.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMcqPapper.getTimeInMin()).isEqualTo(DEFAULT_TIME_IN_MIN);
    }

    @Test
    void createMcqPapperWithExistingId() throws Exception {
        // Create the McqPapper with an existing ID
        mcqPapper.setId("existing_id");

        int databaseSizeBeforeCreate = mcqPapperRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMcqPapperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mcqPapper)))
            .andExpect(status().isBadRequest());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMcqPappers() throws Exception {
        // Initialize the database
        mcqPapperRepository.save(mcqPapper);

        // Get all the mcqPapperList
        restMcqPapperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mcqPapper.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].timeInMin").value(hasItem(DEFAULT_TIME_IN_MIN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMcqPappersWithEagerRelationshipsIsEnabled() throws Exception {
        when(mcqPapperRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMcqPapperMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mcqPapperRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMcqPappersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mcqPapperRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMcqPapperMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mcqPapperRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getMcqPapper() throws Exception {
        // Initialize the database
        mcqPapperRepository.save(mcqPapper);

        // Get the mcqPapper
        restMcqPapperMockMvc
            .perform(get(ENTITY_API_URL_ID, mcqPapper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mcqPapper.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.timeInMin").value(DEFAULT_TIME_IN_MIN));
    }

    @Test
    void getNonExistingMcqPapper() throws Exception {
        // Get the mcqPapper
        restMcqPapperMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewMcqPapper() throws Exception {
        // Initialize the database
        mcqPapperRepository.save(mcqPapper);

        int databaseSizeBeforeUpdate = mcqPapperRepository.findAll().size();

        // Update the mcqPapper
        McqPapper updatedMcqPapper = mcqPapperRepository.findById(mcqPapper.getId()).get();
        updatedMcqPapper.title(UPDATED_TITLE).timeInMin(UPDATED_TIME_IN_MIN);

        restMcqPapperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMcqPapper.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMcqPapper))
            )
            .andExpect(status().isOk());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeUpdate);
        McqPapper testMcqPapper = mcqPapperList.get(mcqPapperList.size() - 1);
        assertThat(testMcqPapper.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMcqPapper.getTimeInMin()).isEqualTo(UPDATED_TIME_IN_MIN);
    }

    @Test
    void putNonExistingMcqPapper() throws Exception {
        int databaseSizeBeforeUpdate = mcqPapperRepository.findAll().size();
        mcqPapper.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMcqPapperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mcqPapper.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mcqPapper))
            )
            .andExpect(status().isBadRequest());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMcqPapper() throws Exception {
        int databaseSizeBeforeUpdate = mcqPapperRepository.findAll().size();
        mcqPapper.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMcqPapperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mcqPapper))
            )
            .andExpect(status().isBadRequest());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMcqPapper() throws Exception {
        int databaseSizeBeforeUpdate = mcqPapperRepository.findAll().size();
        mcqPapper.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMcqPapperMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mcqPapper)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMcqPapperWithPatch() throws Exception {
        // Initialize the database
        mcqPapperRepository.save(mcqPapper);

        int databaseSizeBeforeUpdate = mcqPapperRepository.findAll().size();

        // Update the mcqPapper using partial update
        McqPapper partialUpdatedMcqPapper = new McqPapper();
        partialUpdatedMcqPapper.setId(mcqPapper.getId());

        partialUpdatedMcqPapper.timeInMin(UPDATED_TIME_IN_MIN);

        restMcqPapperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMcqPapper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMcqPapper))
            )
            .andExpect(status().isOk());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeUpdate);
        McqPapper testMcqPapper = mcqPapperList.get(mcqPapperList.size() - 1);
        assertThat(testMcqPapper.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMcqPapper.getTimeInMin()).isEqualTo(UPDATED_TIME_IN_MIN);
    }

    @Test
    void fullUpdateMcqPapperWithPatch() throws Exception {
        // Initialize the database
        mcqPapperRepository.save(mcqPapper);

        int databaseSizeBeforeUpdate = mcqPapperRepository.findAll().size();

        // Update the mcqPapper using partial update
        McqPapper partialUpdatedMcqPapper = new McqPapper();
        partialUpdatedMcqPapper.setId(mcqPapper.getId());

        partialUpdatedMcqPapper.title(UPDATED_TITLE).timeInMin(UPDATED_TIME_IN_MIN);

        restMcqPapperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMcqPapper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMcqPapper))
            )
            .andExpect(status().isOk());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeUpdate);
        McqPapper testMcqPapper = mcqPapperList.get(mcqPapperList.size() - 1);
        assertThat(testMcqPapper.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMcqPapper.getTimeInMin()).isEqualTo(UPDATED_TIME_IN_MIN);
    }

    @Test
    void patchNonExistingMcqPapper() throws Exception {
        int databaseSizeBeforeUpdate = mcqPapperRepository.findAll().size();
        mcqPapper.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMcqPapperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mcqPapper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mcqPapper))
            )
            .andExpect(status().isBadRequest());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMcqPapper() throws Exception {
        int databaseSizeBeforeUpdate = mcqPapperRepository.findAll().size();
        mcqPapper.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMcqPapperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mcqPapper))
            )
            .andExpect(status().isBadRequest());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMcqPapper() throws Exception {
        int databaseSizeBeforeUpdate = mcqPapperRepository.findAll().size();
        mcqPapper.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMcqPapperMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mcqPapper))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the McqPapper in the database
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMcqPapper() throws Exception {
        // Initialize the database
        mcqPapperRepository.save(mcqPapper);

        int databaseSizeBeforeDelete = mcqPapperRepository.findAll().size();

        // Delete the mcqPapper
        restMcqPapperMockMvc
            .perform(delete(ENTITY_API_URL_ID, mcqPapper.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<McqPapper> mcqPapperList = mcqPapperRepository.findAll();
        assertThat(mcqPapperList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

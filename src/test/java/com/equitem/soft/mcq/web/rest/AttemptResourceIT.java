package com.equitem.soft.mcq.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.equitem.soft.mcq.IntegrationTest;
import com.equitem.soft.mcq.domain.Attempt;
import com.equitem.soft.mcq.repository.AttemptRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link AttemptResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttemptResourceIT {

    private static final String DEFAULT_STUDEND_ID = "AAAAAAAAAA";
    private static final String UPDATED_STUDEND_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAPPER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAPPER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_ATTEMPT_NO = 1;
    private static final Integer UPDATED_ATTEMPT_NO = 2;

    private static final String ENTITY_API_URL = "/api/attempts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private MockMvc restAttemptMockMvc;

    private Attempt attempt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attempt createEntity() {
        Attempt attempt = new Attempt().studendId(DEFAULT_STUDEND_ID).papperId(DEFAULT_PAPPER_ID).attemptNo(DEFAULT_ATTEMPT_NO);
        return attempt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attempt createUpdatedEntity() {
        Attempt attempt = new Attempt().studendId(UPDATED_STUDEND_ID).papperId(UPDATED_PAPPER_ID).attemptNo(UPDATED_ATTEMPT_NO);
        return attempt;
    }

    @BeforeEach
    public void initTest() {
        attemptRepository.deleteAll();
        attempt = createEntity();
    }

    @Test
    void createAttempt() throws Exception {
        int databaseSizeBeforeCreate = attemptRepository.findAll().size();
        // Create the Attempt
        restAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attempt)))
            .andExpect(status().isCreated());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeCreate + 1);
        Attempt testAttempt = attemptList.get(attemptList.size() - 1);
        assertThat(testAttempt.getStudendId()).isEqualTo(DEFAULT_STUDEND_ID);
        assertThat(testAttempt.getPapperId()).isEqualTo(DEFAULT_PAPPER_ID);
        assertThat(testAttempt.getAttemptNo()).isEqualTo(DEFAULT_ATTEMPT_NO);
    }

    @Test
    void createAttemptWithExistingId() throws Exception {
        // Create the Attempt with an existing ID
        attempt.setId("existing_id");

        int databaseSizeBeforeCreate = attemptRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attempt)))
            .andExpect(status().isBadRequest());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAttempts() throws Exception {
        // Initialize the database
        attemptRepository.save(attempt);

        // Get all the attemptList
        restAttemptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attempt.getId())))
            .andExpect(jsonPath("$.[*].studendId").value(hasItem(DEFAULT_STUDEND_ID)))
            .andExpect(jsonPath("$.[*].papperId").value(hasItem(DEFAULT_PAPPER_ID)))
            .andExpect(jsonPath("$.[*].attemptNo").value(hasItem(DEFAULT_ATTEMPT_NO)));
    }

    @Test
    void getAttempt() throws Exception {
        // Initialize the database
        attemptRepository.save(attempt);

        // Get the attempt
        restAttemptMockMvc
            .perform(get(ENTITY_API_URL_ID, attempt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attempt.getId()))
            .andExpect(jsonPath("$.studendId").value(DEFAULT_STUDEND_ID))
            .andExpect(jsonPath("$.papperId").value(DEFAULT_PAPPER_ID))
            .andExpect(jsonPath("$.attemptNo").value(DEFAULT_ATTEMPT_NO));
    }

    @Test
    void getNonExistingAttempt() throws Exception {
        // Get the attempt
        restAttemptMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewAttempt() throws Exception {
        // Initialize the database
        attemptRepository.save(attempt);

        int databaseSizeBeforeUpdate = attemptRepository.findAll().size();

        // Update the attempt
        Attempt updatedAttempt = attemptRepository.findById(attempt.getId()).get();
        updatedAttempt.studendId(UPDATED_STUDEND_ID).papperId(UPDATED_PAPPER_ID).attemptNo(UPDATED_ATTEMPT_NO);

        restAttemptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAttempt.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAttempt))
            )
            .andExpect(status().isOk());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeUpdate);
        Attempt testAttempt = attemptList.get(attemptList.size() - 1);
        assertThat(testAttempt.getStudendId()).isEqualTo(UPDATED_STUDEND_ID);
        assertThat(testAttempt.getPapperId()).isEqualTo(UPDATED_PAPPER_ID);
        assertThat(testAttempt.getAttemptNo()).isEqualTo(UPDATED_ATTEMPT_NO);
    }

    @Test
    void putNonExistingAttempt() throws Exception {
        int databaseSizeBeforeUpdate = attemptRepository.findAll().size();
        attempt.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttemptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attempt.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attempt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAttempt() throws Exception {
        int databaseSizeBeforeUpdate = attemptRepository.findAll().size();
        attempt.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttemptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attempt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAttempt() throws Exception {
        int databaseSizeBeforeUpdate = attemptRepository.findAll().size();
        attempt.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttemptMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attempt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAttemptWithPatch() throws Exception {
        // Initialize the database
        attemptRepository.save(attempt);

        int databaseSizeBeforeUpdate = attemptRepository.findAll().size();

        // Update the attempt using partial update
        Attempt partialUpdatedAttempt = new Attempt();
        partialUpdatedAttempt.setId(attempt.getId());

        partialUpdatedAttempt.papperId(UPDATED_PAPPER_ID).attemptNo(UPDATED_ATTEMPT_NO);

        restAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttempt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttempt))
            )
            .andExpect(status().isOk());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeUpdate);
        Attempt testAttempt = attemptList.get(attemptList.size() - 1);
        assertThat(testAttempt.getStudendId()).isEqualTo(DEFAULT_STUDEND_ID);
        assertThat(testAttempt.getPapperId()).isEqualTo(UPDATED_PAPPER_ID);
        assertThat(testAttempt.getAttemptNo()).isEqualTo(UPDATED_ATTEMPT_NO);
    }

    @Test
    void fullUpdateAttemptWithPatch() throws Exception {
        // Initialize the database
        attemptRepository.save(attempt);

        int databaseSizeBeforeUpdate = attemptRepository.findAll().size();

        // Update the attempt using partial update
        Attempt partialUpdatedAttempt = new Attempt();
        partialUpdatedAttempt.setId(attempt.getId());

        partialUpdatedAttempt.studendId(UPDATED_STUDEND_ID).papperId(UPDATED_PAPPER_ID).attemptNo(UPDATED_ATTEMPT_NO);

        restAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttempt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttempt))
            )
            .andExpect(status().isOk());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeUpdate);
        Attempt testAttempt = attemptList.get(attemptList.size() - 1);
        assertThat(testAttempt.getStudendId()).isEqualTo(UPDATED_STUDEND_ID);
        assertThat(testAttempt.getPapperId()).isEqualTo(UPDATED_PAPPER_ID);
        assertThat(testAttempt.getAttemptNo()).isEqualTo(UPDATED_ATTEMPT_NO);
    }

    @Test
    void patchNonExistingAttempt() throws Exception {
        int databaseSizeBeforeUpdate = attemptRepository.findAll().size();
        attempt.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attempt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attempt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAttempt() throws Exception {
        int databaseSizeBeforeUpdate = attemptRepository.findAll().size();
        attempt.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attempt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAttempt() throws Exception {
        int databaseSizeBeforeUpdate = attemptRepository.findAll().size();
        attempt.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttemptMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(attempt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attempt in the database
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAttempt() throws Exception {
        // Initialize the database
        attemptRepository.save(attempt);

        int databaseSizeBeforeDelete = attemptRepository.findAll().size();

        // Delete the attempt
        restAttemptMockMvc
            .perform(delete(ENTITY_API_URL_ID, attempt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attempt> attemptList = attemptRepository.findAll();
        assertThat(attemptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

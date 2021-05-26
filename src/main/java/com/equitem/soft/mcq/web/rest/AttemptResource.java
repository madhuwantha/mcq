package com.equitem.soft.mcq.web.rest;

import com.equitem.soft.mcq.domain.Attempt;
import com.equitem.soft.mcq.repository.AttemptRepository;
import com.equitem.soft.mcq.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.equitem.soft.mcq.domain.Attempt}.
 */
@RestController
@RequestMapping("/api")
public class AttemptResource {

    private final Logger log = LoggerFactory.getLogger(AttemptResource.class);

    private static final String ENTITY_NAME = "attempt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttemptRepository attemptRepository;

    public AttemptResource(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    /**
     * {@code POST  /attempts} : Create a new attempt.
     *
     * @param attempt the attempt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attempt, or with status {@code 400 (Bad Request)} if the attempt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attempts")
    public ResponseEntity<Attempt> createAttempt(@RequestBody Attempt attempt) throws URISyntaxException {
        log.debug("REST request to save Attempt : {}", attempt);
        if (attempt.getId() != null) {
            throw new BadRequestAlertException("A new attempt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Attempt result = attemptRepository.save(attempt);
        return ResponseEntity
            .created(new URI("/api/attempts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /attempts/:id} : Updates an existing attempt.
     *
     * @param id the id of the attempt to save.
     * @param attempt the attempt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attempt,
     * or with status {@code 400 (Bad Request)} if the attempt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attempt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attempts/{id}")
    public ResponseEntity<Attempt> updateAttempt(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Attempt attempt
    ) throws URISyntaxException {
        log.debug("REST request to update Attempt : {}, {}", id, attempt);
        if (attempt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attempt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attemptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Attempt result = attemptRepository.save(attempt);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attempt.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /attempts/:id} : Partial updates given fields of an existing attempt, field will ignore if it is null
     *
     * @param id the id of the attempt to save.
     * @param attempt the attempt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attempt,
     * or with status {@code 400 (Bad Request)} if the attempt is not valid,
     * or with status {@code 404 (Not Found)} if the attempt is not found,
     * or with status {@code 500 (Internal Server Error)} if the attempt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attempts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Attempt> partialUpdateAttempt(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Attempt attempt
    ) throws URISyntaxException {
        log.debug("REST request to partial update Attempt partially : {}, {}", id, attempt);
        if (attempt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attempt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attemptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Attempt> result = attemptRepository
            .findById(attempt.getId())
            .map(
                existingAttempt -> {
                    if (attempt.getStudendId() != null) {
                        existingAttempt.setStudendId(attempt.getStudendId());
                    }
                    if (attempt.getPapperId() != null) {
                        existingAttempt.setPapperId(attempt.getPapperId());
                    }
                    if (attempt.getAttemptNo() != null) {
                        existingAttempt.setAttemptNo(attempt.getAttemptNo());
                    }

                    return existingAttempt;
                }
            )
            .map(attemptRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attempt.getId()));
    }

    /**
     * {@code GET  /attempts} : get all the attempts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attempts in body.
     */
    @GetMapping("/attempts")
    public List<Attempt> getAllAttempts() {
        log.debug("REST request to get all Attempts");
        return attemptRepository.findAll();
    }

    /**
     * {@code GET  /attempts/:id} : get the "id" attempt.
     *
     * @param id the id of the attempt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attempt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attempts/{id}")
    public ResponseEntity<Attempt> getAttempt(@PathVariable String id) {
        log.debug("REST request to get Attempt : {}", id);
        Optional<Attempt> attempt = attemptRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(attempt);
    }

    /**
     * {@code DELETE  /attempts/:id} : delete the "id" attempt.
     *
     * @param id the id of the attempt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attempts/{id}")
    public ResponseEntity<Void> deleteAttempt(@PathVariable String id) {
        log.debug("REST request to delete Attempt : {}", id);
        attemptRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}

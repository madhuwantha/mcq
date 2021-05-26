package com.equitem.soft.mcq.web.rest;

import com.equitem.soft.mcq.domain.McqPapper;
import com.equitem.soft.mcq.repository.McqPapperRepository;
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
 * REST controller for managing {@link com.equitem.soft.mcq.domain.McqPapper}.
 */
@RestController
@RequestMapping("/api")
public class McqPapperResource {

    private final Logger log = LoggerFactory.getLogger(McqPapperResource.class);

    private static final String ENTITY_NAME = "mcqPapper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final McqPapperRepository mcqPapperRepository;

    public McqPapperResource(McqPapperRepository mcqPapperRepository) {
        this.mcqPapperRepository = mcqPapperRepository;
    }

    /**
     * {@code POST  /mcq-pappers} : Create a new mcqPapper.
     *
     * @param mcqPapper the mcqPapper to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mcqPapper, or with status {@code 400 (Bad Request)} if the mcqPapper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mcq-pappers")
    public ResponseEntity<McqPapper> createMcqPapper(@RequestBody McqPapper mcqPapper) throws URISyntaxException {
        log.debug("REST request to save McqPapper : {}", mcqPapper);
        if (mcqPapper.getId() != null) {
            throw new BadRequestAlertException("A new mcqPapper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        McqPapper result = mcqPapperRepository.save(mcqPapper);
        return ResponseEntity
            .created(new URI("/api/mcq-pappers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /mcq-pappers/:id} : Updates an existing mcqPapper.
     *
     * @param id the id of the mcqPapper to save.
     * @param mcqPapper the mcqPapper to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mcqPapper,
     * or with status {@code 400 (Bad Request)} if the mcqPapper is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mcqPapper couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mcq-pappers/{id}")
    public ResponseEntity<McqPapper> updateMcqPapper(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody McqPapper mcqPapper
    ) throws URISyntaxException {
        log.debug("REST request to update McqPapper : {}, {}", id, mcqPapper);
        if (mcqPapper.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mcqPapper.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mcqPapperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        McqPapper result = mcqPapperRepository.save(mcqPapper);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mcqPapper.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /mcq-pappers/:id} : Partial updates given fields of an existing mcqPapper, field will ignore if it is null
     *
     * @param id the id of the mcqPapper to save.
     * @param mcqPapper the mcqPapper to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mcqPapper,
     * or with status {@code 400 (Bad Request)} if the mcqPapper is not valid,
     * or with status {@code 404 (Not Found)} if the mcqPapper is not found,
     * or with status {@code 500 (Internal Server Error)} if the mcqPapper couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mcq-pappers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<McqPapper> partialUpdateMcqPapper(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody McqPapper mcqPapper
    ) throws URISyntaxException {
        log.debug("REST request to partial update McqPapper partially : {}, {}", id, mcqPapper);
        if (mcqPapper.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mcqPapper.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mcqPapperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<McqPapper> result = mcqPapperRepository
            .findById(mcqPapper.getId())
            .map(
                existingMcqPapper -> {
                    if (mcqPapper.getTitle() != null) {
                        existingMcqPapper.setTitle(mcqPapper.getTitle());
                    }
                    if (mcqPapper.getTimeInMin() != null) {
                        existingMcqPapper.setTimeInMin(mcqPapper.getTimeInMin());
                    }

                    return existingMcqPapper;
                }
            )
            .map(mcqPapperRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mcqPapper.getId())
        );
    }

    /**
     * {@code GET  /mcq-pappers} : get all the mcqPappers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mcqPappers in body.
     */
    @GetMapping("/mcq-pappers")
    public List<McqPapper> getAllMcqPappers(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all McqPappers");
        return mcqPapperRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /mcq-pappers/:id} : get the "id" mcqPapper.
     *
     * @param id the id of the mcqPapper to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mcqPapper, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mcq-pappers/{id}")
    public ResponseEntity<McqPapper> getMcqPapper(@PathVariable String id) {
        log.debug("REST request to get McqPapper : {}", id);
        Optional<McqPapper> mcqPapper = mcqPapperRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(mcqPapper);
    }

    /**
     * {@code DELETE  /mcq-pappers/:id} : delete the "id" mcqPapper.
     *
     * @param id the id of the mcqPapper to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mcq-pappers/{id}")
    public ResponseEntity<Void> deleteMcqPapper(@PathVariable String id) {
        log.debug("REST request to delete McqPapper : {}", id);
        mcqPapperRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}

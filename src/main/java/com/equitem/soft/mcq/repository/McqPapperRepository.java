package com.equitem.soft.mcq.repository;

import com.equitem.soft.mcq.domain.McqPapper;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the McqPapper entity.
 */
@Repository
public interface McqPapperRepository extends MongoRepository<McqPapper, String> {
    @Query("{}")
    Page<McqPapper> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<McqPapper> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<McqPapper> findOneWithEagerRelationships(String id);
}

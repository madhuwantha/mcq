package com.equitem.soft.mcq.repository;

import com.equitem.soft.mcq.domain.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Category entity.
 */
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    @Query("{}")
    Page<Category> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Category> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Category> findOneWithEagerRelationships(String id);
}

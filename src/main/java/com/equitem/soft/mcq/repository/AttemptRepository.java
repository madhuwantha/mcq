package com.equitem.soft.mcq.repository;

import com.equitem.soft.mcq.domain.Attempt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Attempt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttemptRepository extends MongoRepository<Attempt, String> {}

package com.equitem.soft.mcq.repository;

import com.equitem.soft.mcq.domain.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Answer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerRepository extends MongoRepository<Answer, String> {}

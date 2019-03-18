package com.spring.bikroy.bikroy.domain.repository;

import com.spring.bikroy.bikroy.domain.model.CodeVarification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CodeVarificationRepository extends MongoRepository<CodeVarification, Integer> {
    Optional<CodeVarification> findById(String s);
}

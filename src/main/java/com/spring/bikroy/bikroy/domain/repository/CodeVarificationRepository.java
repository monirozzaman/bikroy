package com.spring.bikroy.bikroy.domain.repository;

import com.spring.bikroy.bikroy.domain.model.CodeVarification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CodeVarificationRepository extends MongoRepository<CodeVarification, Integer> {
}

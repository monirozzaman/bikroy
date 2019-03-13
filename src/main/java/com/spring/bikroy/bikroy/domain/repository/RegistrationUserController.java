package com.spring.bikroy.bikroy.domain.repository;

import com.spring.bikroy.bikroy.domain.model.RegistrationUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationUserController extends MongoRepository<RegistrationUser, String> {
}

package com.spring.bikroy.bikroy.domain.repository;

import com.spring.bikroy.bikroy.domain.model.RegistrationUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationUserRepository extends MongoRepository<RegistrationUser, String> {

    Optional<RegistrationUser> findByUsername(String s);

    @Query("SELECT u.username FROM registrationUser u WHERE u.email = ?1 and u.password = ?2")
    Optional<RegistrationUser> findUserByEmailAndPassword(String email, String pass);
}

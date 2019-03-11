package com.spring.bikroy.bikroy.domain.repository;

import com.spring.bikroy.bikroy.domain.model.RegistrationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationUserController extends JpaRepository<RegistrationUser, String> {
}

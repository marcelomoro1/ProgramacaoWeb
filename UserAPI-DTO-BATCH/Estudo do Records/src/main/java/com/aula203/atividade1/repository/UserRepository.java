package com.aula203.atividade1.repository;

import com.aula203.atividade1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmailIn(List<String> emails);
}

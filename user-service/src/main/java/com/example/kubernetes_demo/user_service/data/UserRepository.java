package com.example.kubernetes_demo.user_service.data;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByUsername(String username);
}

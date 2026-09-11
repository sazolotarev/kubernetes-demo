package com.example.kubernetes_demo.todo_service.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

public interface TodoListRepository extends ListCrudRepository<TodoList, UUID> {

    Optional<TodoList> findByIdAndUserId(UUID id, String userId);

    List<TodoList> findAllByUserId(String userId);
}

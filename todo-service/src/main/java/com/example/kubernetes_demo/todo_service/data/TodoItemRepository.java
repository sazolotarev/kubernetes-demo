package com.example.kubernetes_demo.todo_service.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

public interface TodoItemRepository extends ListCrudRepository<TodoItem, UUID> {

    Optional<TodoItem> findByIdAndUserId(UUID id, String userId);

    List<TodoItem> findAllByTodoListIdAndUserId(UUID listId, String userId);
}

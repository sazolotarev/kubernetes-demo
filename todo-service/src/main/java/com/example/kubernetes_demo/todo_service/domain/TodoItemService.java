package com.example.kubernetes_demo.todo_service.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kubernetes_demo.todo_service.data.TodoItem;
import com.example.kubernetes_demo.todo_service.data.TodoItemRepository;
import com.example.kubernetes_demo.todo_service.data.TodoListRepository;
import com.example.kubernetes_demo.todo_service.domain.dto.TodoItemDto;
import com.example.kubernetes_demo.todo_service.domain.exception.TodoItemNotFoundException;
import com.example.kubernetes_demo.todo_service.domain.exception.TodoListNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor 
@Slf4j
public class TodoItemService {
    
    private final TodoListRepository todoListRepository;
    private final TodoItemRepository todoItemRepository;
    private final TodoItemMapper todoItemMapper;

    public List<TodoItemDto> getByTodoList(String todoListId, String userId) {
        var todoLists = todoItemRepository.findAllByTodoListIdAndUserId(UUID.fromString(todoListId), userId);

        return todoItemMapper.toDtoList(todoLists);
    }

    @Transactional
    public TodoItemDto create(String todoListId, String description, String userId) {
        var todoListOptional = todoListRepository.findByIdAndUserId(UUID.fromString(todoListId), userId);
        if (todoListOptional.isEmpty()) {
            log.error("Failed to find todo list {} by user {}", todoListId, userId);
            throw new TodoListNotFoundException("Todo list not found", todoListId);
        }

        var todoItem = new TodoItem();
        todoItem.setUserId(userId);
        todoItem.setTodoListId(UUID.fromString(todoListId));
        todoItem.setDescription(description);
        todoItemRepository.save(todoItem);

        log.info("Created todo item {}", todoItem.getId());
        return todoItemMapper.toDto(todoItem);
    }

    @Transactional
    public void delete(String id, String userId) {
        var todoItemOptional = todoItemRepository.findByIdAndUserId(UUID.fromString(id), userId);
        if (todoItemOptional.isEmpty()) {
            log.info("Failed to find todo item {} by user {} for deletion", id, userId);
            throw new TodoItemNotFoundException("Todo item not found", id);
        }

        log.info("Deleting todo item {}", id);
        todoItemRepository.delete(todoItemOptional.get());
    }
}

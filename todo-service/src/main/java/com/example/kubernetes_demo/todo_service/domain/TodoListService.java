package com.example.kubernetes_demo.todo_service.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kubernetes_demo.todo_service.data.TodoItemRepository;
import com.example.kubernetes_demo.todo_service.data.TodoList;
import com.example.kubernetes_demo.todo_service.data.TodoListRepository;
import com.example.kubernetes_demo.todo_service.domain.dto.TodoListDto;
import com.example.kubernetes_demo.todo_service.domain.exception.TodoListNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor 
@Slf4j 
public class TodoListService {
    
    private final TodoListRepository todoListRepository;
    private final TodoItemRepository todoItemRepository;
    private final TodoListMapper todoListMapper;

    public List<TodoListDto> getAllByUser(String userId) {
        var todoLists = todoListRepository.findAllByUserId(userId);

        return todoListMapper.toDtoList(todoLists);
    }

    public TodoListDto get(String id, String userId) {
        var todoListOptional = todoListRepository.findByIdAndUserId(UUID.fromString(id), userId);
        if (todoListOptional.isEmpty()) {
            log.info("Failed to find todo list {} by user {} for deletion", id, userId);
            throw new TodoListNotFoundException("Todo list not found", id);
        }

        return todoListMapper.toDto(todoListOptional.get());
    }

    public TodoListDto create(String name, String userId) {
        var todoList = new TodoList();
        todoList.setName(name);
        todoList.setUserId(userId);
        todoListRepository.save(todoList);

        log.info("Created todo list {}", todoList.getId());
        return todoListMapper.toDto(todoList);
    }

    @Transactional
    public void delete(String id, String userId) {
        var todoListOptional = todoListRepository.findByIdAndUserId(UUID.fromString(id), userId);
        if (todoListOptional.isEmpty()) {
            log.info("Failed to find todo list {} by user {} for deletion", id, userId);
            throw new TodoListNotFoundException("Todo list not found", id);
        }

        var todoItems = todoItemRepository.findAllByTodoListIdAndUserId(UUID.fromString(id), userId);
        for (var todoItem : todoItems) {
            log.info("Deleting todo item {}", todoItem.getId());
            todoItemRepository.delete(todoItem);
        }

        log.info("Deleting todo list {}", id);
        todoListRepository.delete(todoListOptional.get());
    }
}

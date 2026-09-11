package com.example.kubernetes_demo.todo_service.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.kubernetes_demo.todo_service.api.dto.CreateTodoItemRequest;
import com.example.kubernetes_demo.todo_service.api.dto.CreateTodoItemResponse;
import com.example.kubernetes_demo.todo_service.api.dto.CreateTodoListRequest;
import com.example.kubernetes_demo.todo_service.api.dto.CreateTodoListResponse;
import com.example.kubernetes_demo.todo_service.api.dto.DeleteTodoItemResponse;
import com.example.kubernetes_demo.todo_service.api.dto.DeleteTodoListResponse;
import com.example.kubernetes_demo.todo_service.api.dto.TodoItemsResponse;
import com.example.kubernetes_demo.todo_service.api.dto.TodoListResponse;
import com.example.kubernetes_demo.todo_service.api.dto.TodoListsResponse;
import com.example.kubernetes_demo.todo_service.domain.TodoItemService;
import com.example.kubernetes_demo.todo_service.domain.TodoListService;
import com.example.kubernetes_demo.todo_service.domain.exception.TodoItemNotFoundException;
import com.example.kubernetes_demo.todo_service.domain.exception.TodoListNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
public class TodoApiController {
    
    private final TodoListService todoListService;
    private final TodoItemService todoItemService;
    private final TodoApiResponseMapper todoApiResponseMapper;

    @ExceptionHandler(TodoListNotFoundException.class)
    public ErrorResponse handleTodoListNotFound(TodoListNotFoundException e) {
        return ErrorResponse.builder(e, HttpStatus.NOT_FOUND, "Sorry, this To-Do list does not exist").build();
    }

    @ExceptionHandler(TodoItemNotFoundException.class)
    public ErrorResponse handleTodoItemNotFound(TodoItemNotFoundException e) {
        return ErrorResponse.builder(e, HttpStatus.NOT_FOUND, "Sorry, this To-Do item does not exist").build();
    }

    @GetMapping("/api/todo/lists")
    public TodoListsResponse getTodoLists(@SessionAttribute String userId) {
        var todoLists = todoListService.getAllByUser(userId);

        return todoApiResponseMapper.toTodoListsResponse(todoLists);
    }

    @GetMapping("/api/todo/lists/{id}")
    public TodoListResponse getTodoList(@PathVariable String id, @SessionAttribute String userId) {
        var todoList = todoListService.get(id, userId);

        return todoApiResponseMapper.toTodoListResponse(todoList);
    }

    @PostMapping("/api/todo/lists")
    public CreateTodoListResponse createTodoList(@RequestBody @Validated CreateTodoListRequest request, @SessionAttribute String userId) {
        var todoLists = todoListService.create(request.name(), userId);
        
        return todoApiResponseMapper.toCreateTodoListResponse(todoLists);
    }

    @DeleteMapping("/api/todo/lists/{id}")
    public DeleteTodoListResponse deleteTodoList(@PathVariable String id, @SessionAttribute String userId) {
        todoListService.delete(id, userId);

        return new DeleteTodoListResponse(id);
    }

    @GetMapping("/api/todo/items")
    public TodoItemsResponse getTodoItems(@RequestParam String todoListId, @SessionAttribute String userId) {
        var todoItems = todoItemService.getByTodoList(todoListId, userId);

        return todoApiResponseMapper.toTodoItemsResponse(todoItems);
    }

    @PostMapping("/api/todo/items")
    public CreateTodoItemResponse createTodoItem(@RequestBody @Validated CreateTodoItemRequest request, @SessionAttribute String userId) {
        var todoItems = todoItemService.create(request.todoListId(), request.description(), userId);
        
        return todoApiResponseMapper.toCreateTodoItemResponse(todoItems);
    }

    @DeleteMapping("/api/todo/items/{id}")
    public DeleteTodoItemResponse deleteTodoItem(@PathVariable String id, @SessionAttribute String userId) {
        todoItemService.delete(id, userId);

        return new DeleteTodoItemResponse(id);
    }
}

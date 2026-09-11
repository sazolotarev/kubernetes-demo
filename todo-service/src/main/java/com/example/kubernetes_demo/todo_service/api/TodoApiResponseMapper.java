package com.example.kubernetes_demo.todo_service.api;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.example.kubernetes_demo.todo_service.api.dto.CreateTodoItemResponse;
import com.example.kubernetes_demo.todo_service.api.dto.CreateTodoListResponse;
import com.example.kubernetes_demo.todo_service.api.dto.TodoItemsResponse;
import com.example.kubernetes_demo.todo_service.api.dto.TodoListResponse;
import com.example.kubernetes_demo.todo_service.api.dto.TodoListsResponse;
import com.example.kubernetes_demo.todo_service.domain.dto.TodoItemDto;
import com.example.kubernetes_demo.todo_service.domain.dto.TodoListDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TodoApiResponseMapper {
    
    TodoListResponse toTodoListResponse(TodoListDto todoList);

    CreateTodoListResponse toCreateTodoListResponse(TodoListDto todoList);

    List<TodoListsResponse.Entry> toTodoListListEntries(List<TodoListDto> todoLists);

    default TodoListsResponse toTodoListsResponse(List<TodoListDto> todoLists) {
        return new TodoListsResponse(toTodoListListEntries(todoLists));
    }

    CreateTodoItemResponse toCreateTodoItemResponse(TodoItemDto todoItem);

    List<TodoItemsResponse.Entry> toTodoItemListEntries(List<TodoItemDto> todoItems);

    default TodoItemsResponse toTodoItemsResponse(List<TodoItemDto> todoItems) {
        return new TodoItemsResponse(toTodoItemListEntries(todoItems));
    }
}

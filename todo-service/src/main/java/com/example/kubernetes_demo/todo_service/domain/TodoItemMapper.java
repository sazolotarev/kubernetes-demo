package com.example.kubernetes_demo.todo_service.domain;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.example.kubernetes_demo.todo_service.data.TodoItem;
import com.example.kubernetes_demo.todo_service.domain.dto.TodoItemDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TodoItemMapper {
    
    TodoItemDto toDto(TodoItem todoItem);

    List<TodoItemDto> toDtoList(List<TodoItem> todoItems);
}

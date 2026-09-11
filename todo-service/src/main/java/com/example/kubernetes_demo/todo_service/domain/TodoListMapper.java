package com.example.kubernetes_demo.todo_service.domain;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.example.kubernetes_demo.todo_service.data.TodoList;
import com.example.kubernetes_demo.todo_service.domain.dto.TodoListDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TodoListMapper {
    
    TodoListDto toDto(TodoList todoList);

    List<TodoListDto> toDtoList(List<TodoList> todoLists);
}

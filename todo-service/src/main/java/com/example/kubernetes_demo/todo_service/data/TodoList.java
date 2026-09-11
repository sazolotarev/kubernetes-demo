package com.example.kubernetes_demo.todo_service.data;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table("todo_lists")
@Getter
@Setter
public class TodoList {

    @Id
    private UUID id;

    private Instant createdAt = Instant.now();

    private String userId;

    private String name;
}

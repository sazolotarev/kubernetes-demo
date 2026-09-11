package com.example.kubernetes_demo.todo_service.data;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table("todo_items")
@Getter
@Setter
public class TodoItem {

    @Id
    private UUID id;

    private Instant createdAt = Instant.now();

    private String userId;

    private UUID todoListId;

    private String description;
}

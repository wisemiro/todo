package com.example.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue
    private Long id;
    private String task;

    @Builder
    public Todo(Long id, String task) {
        this.id = id;
        this.task = task;
    }
}

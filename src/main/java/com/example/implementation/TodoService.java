package com.example.implementation;

import com.example.data_transfer_object.TodoDTO;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface TodoService {
    TodoDTO create(TodoDTO dto);
    List<TodoDTO> findAll();
    TodoDTO findById(Long id);
    void update(Long id, TodoDTO dto);
    void delete(Long id);
}

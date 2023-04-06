package com.example.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import com.example.data_transfer_object.TodoDTO;
import com.example.mapper.TodoMapper;
import com.example.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//implements: declare a behavior that classes must implement.
@Service
public class TodoImplementation implements TodoService {

    @Autowired
    private TodoMapper builder;

    @Autowired
    private TodoRepository repository;

    @Override
    public TodoDTO create(TodoDTO dto) {
        return Optional.of(dto)
                .map(builder::build)
                .map(repository::save)
                .map(builder::build)
                .orElseThrow(() -> new PersistenceException("Failed to create entity - " + dto));
    }

    @Override
    public List<TodoDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(builder::build)
                .collect(Collectors.toList());

    }

    @Override
    public TodoDTO findById(Long id) {
        return repository.findById(id)
                .map(builder::build)
                .orElseThrow(() -> new EntityNotFoundException("No such entity for id " + id));
    }

    @Override
    public void update(Long id, TodoDTO dto) {
        repository.findById(id)
                .map(domain -> builder.build(dto, domain))
                .map(repository::save)
                .orElseThrow(() -> new EntityNotFoundException("No such entity for id " + id));
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .ifPresentOrElse(
                        (value) -> repository.delete(value),
                        () -> {
                            throw new EntityNotFoundException("No such entity for id " + id);
                        });
    }
}

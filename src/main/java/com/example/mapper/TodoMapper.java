package com.example.mapper;

import com.example.data_transfer_object.TodoDTO;
import com.example.models.Todo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//Spring @Autowired annotation is used for automatic dependency injection.
// Spring framework is built on dependency injection, and
// we inject the class dependencies through spring bean configuration file.

//Spring @Component annotation is used to denote a class as Component.
// It means that Spring framework will autodetect these classes for
// dependency injection when annotation-based configuration and classpath scanning is used.


@Component
public class TodoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Todo build(TodoDTO dto) {
        return Todo.builder().id(dto.getId()).task(dto.getTask()).build();
    }

    public TodoDTO build(Todo domain) {
        return TodoDTO.builder().id(domain.getId()).task(domain.getTask()).build();
    }

    public Todo build(TodoDTO dto, Todo domain) {
        modelMapper.map(dto, domain);
        return domain;
    }

}
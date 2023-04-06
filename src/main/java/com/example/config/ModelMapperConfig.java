package com.example.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration annotation is part of the spring core framework.
// Spring Configuration annotation indicates that the class has @Bean definition methods.
// So Spring container can process the class and generate Spring Beans to be used in the application.
@Configuration
public class ModelMapperConfig {

    @Bean //method level annotation
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

}
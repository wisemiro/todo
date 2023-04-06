package com.example.data_transfer_object;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TodoDTO {
    Long id;
    @NotNull
    String task;
}
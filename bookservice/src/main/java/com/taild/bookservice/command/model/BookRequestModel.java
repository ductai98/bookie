package com.taild.bookservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookRequestModel {

    private String id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    private String name;

    @NotBlank(message = "Author is required")

    private String author;

    private Boolean isAvailable;
}

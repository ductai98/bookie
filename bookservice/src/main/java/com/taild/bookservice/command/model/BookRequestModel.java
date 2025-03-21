package com.taild.bookservice.command.model;

import lombok.Data;

@Data
public class BookRequestModel {

    private String id;

    private String name;

    private String author;

    private Boolean isAvailable;
}

package com.taild.bookservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateEvent {

    private String id;

    private String name;

    private String author;

    private Boolean isAvailable;
}

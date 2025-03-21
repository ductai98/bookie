package com.taild.bookservice.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetBookByIdQuery {

    private String id;
}

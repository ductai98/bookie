package com.taild.bookservice.query.controller;


import com.taild.bookservice.query.model.BookResponseModel;
import com.taild.bookservice.query.queries.GetAllBooksQuery;
import com.taild.bookservice.query.queries.GetBookByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<BookResponseModel> getAllBooks() {
        GetAllBooksQuery query = new GetAllBooksQuery();

        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
    }

    @GetMapping("/{id}")
    public BookResponseModel getBookById(@PathVariable String id) {
        GetBookByIdQuery query = new GetBookByIdQuery(id);

        return queryGateway.query(query, ResponseTypes.instanceOf(BookResponseModel.class)).join();
    }

}

package com.taild.bookservice.query.controller;


import com.taild.bookservice.query.model.BookResponseModel;
import com.taild.bookservice.query.queries.GetAllBooksQuery;
import com.taild.bookservice.query.queries.GetBookByIdQuery;
import com.taild.commonservice.model.BookResponseCommonModel;
import com.taild.commonservice.services.KafkaService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private KafkaService kafkaService;

    @GetMapping
    public List<BookResponseModel> getAllBooks() {
        GetAllBooksQuery query = new GetAllBooksQuery();

        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
    }

    @GetMapping("/{id}")
    public BookResponseCommonModel getBookById(@PathVariable String id) {
        GetBookByIdQuery query = new GetBookByIdQuery(id);

        return queryGateway.query(query, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message) {
        kafkaService.sendMessage("test", message);
    }

}

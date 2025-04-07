package com.taild.bookservice.query.projection;


import com.taild.bookservice.command.data.Book;
import com.taild.bookservice.command.data.BookRepository;
import com.taild.bookservice.query.model.BookResponseModel;
import com.taild.bookservice.query.queries.GetAllBooksQuery;
import com.taild.bookservice.query.queries.GetBookByIdQuery;
import com.taild.commonservice.model.BookResponseCommonModel;
import org.axonframework.queryhandling.QueryHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookProjection {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBooksQuery query) {
        List<Book> list = bookRepository.findAll();

        return list.stream().map(
                book -> modelMapper.map(book, BookResponseModel.class)
        ).toList();
    }


    @QueryHandler
    public BookResponseCommonModel handle(GetBookByIdQuery query) {
        Book book = bookRepository.findById(query.getId())
                .orElseThrow(() -> new RuntimeException("Book not found id = " + query.getId()))  ;

        return modelMapper.map(book, BookResponseCommonModel.class);
    }
}

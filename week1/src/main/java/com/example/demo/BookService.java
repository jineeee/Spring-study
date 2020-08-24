package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.awt.print.Book;

@Service
public class BookService implements ApplicationRunner {
    BookRepository bookRepository;

    @Autowired
    public void setBookRepo(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(bookRepository.getClass());
    }
}

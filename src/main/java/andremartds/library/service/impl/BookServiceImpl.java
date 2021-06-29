package andremartds.library.service.impl;

import andremartds.library.model.entity.Book;
import andremartds.library.model.entity.repository.BookRepository;
import andremartds.library.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
}

package andremartds.library.api.resource;

import andremartds.library.api.dto.BookDTO;
import andremartds.library.api.errors.ApiErrors;
import andremartds.library.model.entity.Book;
import andremartds.library.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books/")
public class BookController {

    @Autowired
    public BookService service;
    private ModelMapper mapper;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto) {
        // crio um book retornando os dados do dto
        Book entity = mapper.map(dto, Book.class);
        entity = service.save(entity);
       return mapper.map(entity, BookDTO.class);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return new ApiErrors(bindingResult);
    }
}

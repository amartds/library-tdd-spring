package andremartds.library.api.resource;

import andremartds.library.api.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books/")
public class BookController {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO dto) {
       BookDTO dto = new BookDTO();
       dto.setAutor("André Martins");
       dto.setTitle("Meu livro");
       dto.setId(1L);
       dto.setIsbn("123123");
       return dto;
    }
}

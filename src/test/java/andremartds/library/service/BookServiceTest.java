package andremartds.library.service;

import andremartds.library.api.dto.BookDTO;
import andremartds.library.api.exceptions.BusinessException;
import andremartds.library.model.entity.Book;
import andremartds.library.model.entity.repository.BookRepository;
import andremartds.library.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
   BookService service;

   @MockBean
   BookRepository respository;

   @BeforeEach
   public void setUp() {
       this.service = new BookServiceImpl(respository);
   }

   @Test
   @DisplayName("deve salvar um livro")
   public void saveBookTest() {
       // expected
       Book book = this.createNewBook();
       Mockito.when(respository.existsByIsbn(Mockito.anyString())).thenReturn(false);

       Mockito.when(respository.save(book))
               .thenReturn(Book.builder().id(1L).isbn("123").autor("Fulano").title("aventuras").build());

       // execute
       Book savedBook = service.save(book);

       // verification
       assertThat(savedBook.getId()).isNotNull();
       assertThat(savedBook.getIsbn()).isEqualTo("123");
       assertThat(savedBook.getAutor()).isEqualTo("Fulano");
       assertThat(savedBook.getTitle()).isEqualTo("aventuras");
   }

    @Test
    @DisplayName("Deve lancar erro de negocio ao tentar salvar um livro com isbn duplicado")
    public void shouldNotSaveBookWithIsbnDuplicated() {
       // cenario
       Book book = createNewBook();
       Mockito.when(respository.existsByIsbn(Mockito.anyString())).thenReturn(true);

       //execucao
       Throwable ex = Assertions.catchThrowable(() -> service.save(book));
       assertThat(ex)
               .isInstanceOf(BusinessException.class)
               .hasMessage("isbn já cadastrado.");

       Mockito.verify(respository, Mockito.never()).save(book);
   }

    private Book createNewBook() {
        return Book.builder().autor("André Martins").title("Meu livro").isbn("123123").build();
    }
}

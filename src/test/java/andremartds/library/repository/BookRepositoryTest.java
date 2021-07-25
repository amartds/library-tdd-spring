package andremartds.library.repository;

import andremartds.library.model.entity.Book;
import andremartds.library.model.entity.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    TestEntityManager em;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("deve retornar verdadeiro quando existir um livro na base de dados")
    public void returnTrueWhenIsbnExists() {
        //cenario
        String isbn = "123";
        Book book = createNewBook();
        em.persist(book);

        //execucao
        boolean exist = repository.existsByIsbn(isbn);

        //test
        assertThat(exist).isTrue();
    }
    private Book createNewBook() {
        return Book.builder().autor("André Martins").title("Meu livro").isbn("123").build();
    }

}

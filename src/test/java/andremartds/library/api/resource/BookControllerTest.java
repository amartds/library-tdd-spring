package andremartds.library.api.resource;

import andremartds.library.api.dto.BookDTO;
import andremartds.library.api.exceptions.BusinessException;
import andremartds.library.model.entity.Book;
import andremartds.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

   static String BOOk_API = "/api/books/";

   @Autowired
   MockMvc mvc;

   @MockBean
   BookService service;

   // validação de integridade ###############################
   @Test
   @DisplayName("Deve criar um livro com sucesso")
   public void createBookTest() throws Exception {

       BookDTO dto = BookDTO.builder().autor("André Martins").title("Meu livro").isbn("aaa").build();
       Book savedBook = Book.builder().id(12L).autor("André Martins").title("Meu livro").isbn("aaa").build(); // recebendo os dados fake no book

       BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);
       
       String json = new ObjectMapper().writeValueAsString(dto);

       MockHttpServletRequestBuilder request = MockMvcRequestBuilders // mock para api
               .post(BOOk_API)
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .content(json);

       mvc.perform(request)
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id").value(12L))
               .andExpect(jsonPath("title").value(dto.getTitle()))
               .andExpect(jsonPath("autor").value(dto.getAutor()))
               .andExpect(jsonPath("isbn").value(dto.getIsbn()));
   }

    @Test
    @DisplayName("Deve lançar um erro de validação ao não ter os dados corretos de um livro")
    public void createInvalidBookTest() throws Exception {
        var json = new ObjectMapper().writeValueAsString(new BookDTO());


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOk_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(3)));
    }

    // ############### validação de regra de negócio  ##################
    @Test
    @DisplayName("Deve lançar um erro ao tentar cadastrar um isbn com outro igual existente")
    public void createValidDuplicatedIsbn() throws Exception {

        BookDTO dto = BookDTO.builder().autor("André Martins").title("Meu livro").isbn("bbb").build();

        BDDMockito.given(service.save(Mockito.any(Book.class))).willThrow(new BusinessException("isbn ja existe"));
        String json = new ObjectMapper().writeValueAsString(dto); // converte um json em string

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders  // batendo na api
                .post(BOOk_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("isbn ja existe"));

    }

    private BookDTO createNewBook() {
        return BookDTO.builder().autor("André Martins").title("Meu livro").isbn("123123").build();
    }

}

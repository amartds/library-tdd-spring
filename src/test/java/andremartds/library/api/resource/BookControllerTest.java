package andremartds.library.api.resource;

import andremartds.library.api.dto.BookDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.print.Book;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

   static String BOOk_API = "/api/books/";

   @Autowired
   MockMvc mvc;

   @Test
   @DisplayName("Deve criar um livro com sucesso")
   public void createBookTest() throws Exception {
       BookDTO dto = BookDTO.builder().autor("André Martins").title("Meu livro").isbn("123123").build();
       String json = new ObjectMapper().writeValueAsString(dto); // converte um json em string

       // definido onde será aplicado o teste e quais sao as especificidades de entrada
       MockHttpServletRequestBuilder request = MockMvcRequestBuilders
               .post(BOOk_API)
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .content(json);

       // expectativas do nosso teste
       mvc.perform(request)
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id").isNotEmpty())
               .andExpect(jsonPath("title").value(dto.getTitle()))
               .andExpect(jsonPath("autor").value(dto.getAutor()))
               .andExpect(jsonPath("isbn").value(dto.getIsbn()));
   }

    @Test
    @DisplayName("Deve lançar um erro de validação ao não ter os dados corretos de um livro")
    public void createInvalidBookTest() {

    }
   
}

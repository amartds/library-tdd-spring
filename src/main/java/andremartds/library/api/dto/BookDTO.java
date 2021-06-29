package andremartds.library.api.dto;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    @NotEmpty(message = "title não pode ser nulo")
    private String title;
    @NotEmpty(message = "autor não pode ser nulo")
    private String autor;
    @NotEmpty(message = "isbn não pode ser nulo")
    private String isbn;
}

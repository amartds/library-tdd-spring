package andremartds.library.api.dto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO { private Long id; private String title; private String autor; private String isbn; }

package andremartds.library.api.dto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO { private Long id; private String title; private String autor; private String isbn; }

package fateyev.ru.showcaseOfProducts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ShowcaseDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2, max = 250, message = "Address should be between 2 and 250 characters")
    private String address;

    @NotEmpty(message = "Type should not be empty")
    @Size(min = 2, max = 50, message = "Type should be between 2 and 50 characters")
    private String type;
}

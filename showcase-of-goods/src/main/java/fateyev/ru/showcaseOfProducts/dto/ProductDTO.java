package fateyev.ru.showcaseOfProducts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {


    @NotEmpty(message = "Position should not be empty")
    @Size(min = 2, max = 100, message = "Position should be between 2 and 100 characters")
    private String position;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @NotEmpty(message = "Type should not be empty")
    @Size(min = 2, max = 50, message = "Type should be between 2 and 50 characters")
    private String type;

    @Min(value = 0, message = "Price should be greater than 0")
    @NotNull
    private BigDecimal price;
}

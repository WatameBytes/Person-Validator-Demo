import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Address {
    @NotBlank(message = "Street address cannot be blank")
    private String streetAddress;

    @Min(value = 1, message = "Zip code must be positive")
    private long zipCode;
}

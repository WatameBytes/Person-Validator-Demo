import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Person {
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    private String middleName; // Optional, no validation

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Min(value = 0, message = "Age must be non-negative")
    private int age;

    @Valid
    @NotNull(message = "Address cannot be null")
    private Address address;
}

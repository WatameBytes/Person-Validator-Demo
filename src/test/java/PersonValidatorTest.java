import org.junit.jupiter.api.Test;
import jakarta.validation.ValidationException;
import static org.junit.jupiter.api.Assertions.*;

public class PersonValidatorTest {

    // Helper method to build a valid Person object
    private Person createValidPerson() {
        Address address = new Address();
        address.setStreetAddress("123 Main St");
        address.setZipCode(12345L);

        Person person = new Person();
        person.setFirstName("Rex");
        person.setMiddleName("J");
        person.setLastName("Smith");
        person.setAge(30);
        person.setAddress(address);

        return person;
    }

    @Test
    void validate_validPerson_noException() {
        Person validPerson = createValidPerson();

        assertDoesNotThrow(() -> ValidatorUtil.validate(validPerson));
    }

    @Test
    void validate_blankFirstName_throwsException() {
        Person person = createValidPerson();
        person.setFirstName("   ");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidatorUtil.validate(person));

        assertTrue(exception.getMessage().contains("firstName: First name cannot be blank"));
    }

    @Test
    void validate_nullLastName_throwsException() {
        Person person = createValidPerson();
        person.setLastName(null);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidatorUtil.validate(person));

        assertTrue(exception.getMessage().contains("lastName: Last name cannot be blank"));
    }

    @Test
    void validate_negativeAge_throwsException() {
        Person person = createValidPerson();
        person.setAge(-1);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidatorUtil.validate(person));

        assertTrue(exception.getMessage().contains("age: Age must be non-negative"));
    }

    @Test
    void validate_nullAddress_throwsException() {
        Person person = createValidPerson();
        person.setAddress(null);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidatorUtil.validate(person));

        assertTrue(exception.getMessage().contains("address: Address cannot be null"));
    }

    @Test
    void validate_blankStreetAddress_throwsException() {
        Person person = createValidPerson();
        person.getAddress().setStreetAddress("  ");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidatorUtil.validate(person));

        assertTrue(exception.getMessage().contains("address.streetAddress: Street address cannot be blank"));
    }

    @Test
    void validate_invalidZipCode_throwsException() {
        Person person = createValidPerson();
        person.getAddress().setZipCode(0L);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidatorUtil.validate(person));

        assertTrue(exception.getMessage().contains("address.zipCode: Zip code must be positive"));
    }

    @Test
    void validate_multipleErrors_reportsAllErrors() {
        Person person = createValidPerson();
        person.setFirstName("");
        person.setLastName("");
        person.getAddress().setZipCode(-100L);
        person.getAddress().setStreetAddress("");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidatorUtil.validate(person));

        String message = exception.getMessage();
        assertTrue(message.contains("firstName: First name cannot be blank"));
        assertTrue(message.contains("lastName: Last name cannot be blank"));
        assertTrue(message.contains("address.streetAddress: Street address cannot be blank"));
        assertTrue(message.contains("address.zipCode: Zip code must be positive"));
    }
}

import jakarta.validation.ValidationException;

public class MainExample {
    public static void main(String[] args) {
        Address address = new Address();
        address.setStreetAddress("123 Broadway");
        address.setZipCode(0); // Invalid zipCode for demo purposes

        Person person = new Person();
        person.setFirstName("Rex");
        person.setMiddleName("J");
        person.setLastName(""); // Invalid lastName for demo
        person.setAge(25);
        person.setAddress(address);

        try {
            ValidatorUtil.validate(person);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }
}

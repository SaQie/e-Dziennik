package pl.edziennik.application.common.mapper;


import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.domain.address.Address;

public class AddressMapper {

    private AddressMapper() {
    }
    public static Address mapToAddress(CreateStudentCommand command) {
        return new Address(command.address(), command.city(), command.postalCode());
    }
}

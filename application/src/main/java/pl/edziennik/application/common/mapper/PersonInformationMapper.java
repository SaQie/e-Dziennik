package pl.edziennik.application.common.mapper;


import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.common.valueobject.PersonInformation;

public class PersonInformationMapper {

    private PersonInformationMapper() {

    }
    public static PersonInformation mapToPersonInformation(CreateStudentCommand command) {
        return PersonInformation.of(command.firstName(), command.lastName(), command.phoneNumber(),
                command.pesel());
    }
}

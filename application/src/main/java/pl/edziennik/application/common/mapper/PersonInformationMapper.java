package pl.edziennik.application.common.mapper;


import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.domain.personinfromation.PersonInformation;
import pl.edziennik.domain.personinfromation.Pesel;
import pl.edziennik.domain.personinfromation.PhoneNumber;

public class PersonInformationMapper {

    private PersonInformationMapper() {

    }
    public static PersonInformation mapToPersonInformation(CreateStudentCommand command) {
        return PersonInformation.of(command.firstName(), command.lastName(), PhoneNumber.of(command.phoneNumber()), Pesel.of(command.pesel()));
    }
}

package pl.edziennik.eDziennik.domain.personinformation.dto.mapper;

import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;

public class PersonInformationMapper {

    private PersonInformationMapper() {

    }

    public static PersonInformation mapToPersonInformation(TeacherRequestApiDto dto) {
        PersonInformation personInformation = new PersonInformation();
        personInformation.setPesel(dto.pesel());
        personInformation.setFirstName(dto.firstName());
        personInformation.setLastName(dto.lastName());
        personInformation.setPhoneNumber(dto.phoneNumber());
        return personInformation;
    }

    public static PersonInformation mapToPersonInformation(StudentRequestApiDto dto) {
        PersonInformation personInformation = new PersonInformation();
        personInformation.setPesel(dto.pesel());
        personInformation.setFirstName(dto.firstName());
        personInformation.setLastName(dto.lastName());
        personInformation.setPhoneNumber(dto.phoneNumber());
        return personInformation;
    }

    public static PersonInformation mapToPersonInformation(ParentRequestApiDto dto) {
        PersonInformation personInformation = new PersonInformation();
        personInformation.setPesel(dto.pesel());
        personInformation.setFirstName(dto.firstName());
        personInformation.setLastName(dto.lastName());
        personInformation.setPhoneNumber(dto.phoneNumber());
        return personInformation;
    }

}

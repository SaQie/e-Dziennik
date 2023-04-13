package pl.edziennik.eDziennik.domain.personinformation.dto.mapper;

import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.PhoneNumber;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;

public class PersonInformationMapper {

    private PersonInformationMapper() {

    }

    public static PersonInformation mapToPersonInformation(TeacherRequestApiDto dto) {
        return PersonInformation.of(dto.firstName(), dto.lastName(), PhoneNumber.of(dto.phoneNumber()), Pesel.of(dto.pesel()));
    }

    public static PersonInformation mapToPersonInformation(StudentRequestApiDto dto) {
        return PersonInformation.of(dto.firstName(), dto.lastName(), PhoneNumber.of(dto.phoneNumber()), Pesel.of(dto.pesel()));
    }

    public static PersonInformation mapToPersonInformation(ParentRequestApiDto dto) {
        return PersonInformation.of(dto.firstName(), dto.lastName(), PhoneNumber.of(dto.phoneNumber()), Pesel.of(dto.pesel()));
    }

}

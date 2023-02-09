package pl.edziennik.eDziennik.server.student.domain.dto.mapper;

import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.personinformation.PersonInformationMapper;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;

public class StudentMapper {


    private StudentMapper() {
    }

    public static StudentResponseApiDto toDto(Student entity) {
        return StudentResponseApiDto.builder()
                .id(entity.getId())
                .username(entity.getUser().getUsername())
                .firstName(entity.getUser().getPersonInformation().getFirstName())
                .lastName(entity.getUser().getPersonInformation().getLastName())
                .address(entity.getUser().getAddress().getAddress())
                .postalCode(entity.getUser().getAddress().getPostalCode())
                .city(entity.getUser().getAddress().getCity())
                .pesel(entity.getUser().getPersonInformation().getPesel())
                .parentFirstName(entity.getParentFirstName())
                .parentLastName(entity.getParentLastName())
                .parentPhoneNumber(entity.getParentPhoneNumber())
                .email(entity.getUser().getEmail())
                .school(SchoolMapper.toSimpleDto(entity.getSchool()))
                .schoolClass(SchoolClassMapper.toSimpleDto(entity.getSchoolClass()))
                .role(entity.getUser().getRole().getName())
                .build();

    }

    public static Student toEntity(StudentRequestApiDto dto) {
        return new Student(
                dto.getParentFirstName(),
                dto.getParentLastName(),
                dto.getParentPhoneNumber()
        );
    }
}

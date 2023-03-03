package pl.edziennik.eDziennik.domain.student.dto.mapper;

import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.domain.parent.domain.dto.mapper.ParentMapper;
import pl.edziennik.eDziennik.domain.personinformation.dto.mapper.PersonInformationMapper;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.school.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.domain.schoolclass.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentSimpleResponseApiDto;

public class StudentMapper {


    private StudentMapper() {
    }

    public static StudentResponseApiDto toDto(Student entity) {
        return StudentResponseApiDto.builder()
                .id(entity.getId())
                .username(entity.getUser().getUsername())
                .firstName(entity.getPersonInformation().getFirstName())
                .lastName(entity.getPersonInformation().getLastName())
                .address(entity.getAddress().getAddress())
                .postalCode(entity.getAddress().getPostalCode())
                .city(entity.getAddress().getCity())
                .pesel(entity.getPersonInformation().getPesel())
                .fullName(entity.getPersonInformation().getFullName())
                .email(entity.getUser().getEmail())
                .parent(ParentMapper.toSimpleDto(entity.getParent()))
                .school(SchoolMapper.toSimpleDto(entity.getSchool()))
                .schoolClass(SchoolClassMapper.toSimpleDto(entity.getSchoolClass()))
                .role(entity.getUser().getRole().getName())
                .build();

    }

    public static StudentSimpleResponseApiDto toSimpleDto(Student student) {
        return new StudentSimpleResponseApiDto(student.getId(), student.getPersonInformation().getFullName());
    }

    public static Student toEntity(StudentRequestApiDto dto) {
        return new Student(
                PersonInformationMapper.mapToPersonInformation(dto),
                AddressMapper.mapToAddress(dto)
        );
    }
}

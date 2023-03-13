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

import java.util.List;

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
                .phoneNumber(entity.getPersonInformation().getPhoneNumber())
                .parent(ParentMapper.toSimpleDto(entity.getParent()))
                .school(SchoolMapper.toSimpleDto(entity.getSchool()))
                .schoolClass(SchoolClassMapper.toSimpleDto(entity.getSchoolClass()))
                .role(entity.getUser().getRole().getName())
                .build();

    }

    public static List<StudentResponseApiDto> toDto(List<Student> entities) {
        return entities.stream().map(StudentMapper::toDto).toList();
    }

    public static StudentRequestApiDto toRequestDto(Student entity) {
        return StudentRequestApiDto.builder()
                .username(entity.getUser().getUsername())
                .firstName(entity.getPersonInformation().getFirstName())
                .lastName(entity.getPersonInformation().getLastName())
                .address(entity.getAddress().getAddress())
                .postalCode(entity.getAddress().getPostalCode())
                .city(entity.getAddress().getCity())
                .pesel(entity.getPersonInformation().getPesel())
                .email(entity.getUser().getEmail())
                .phoneNumber(entity.getPersonInformation().getPhoneNumber())
                .idSchool(entity.getSchool().getId())
                .idSchoolClass(entity.getSchoolClass().getId())
                .build();
    }

    public static StudentSimpleResponseApiDto toSimpleDto(Student student) {
        if (student != null) {
            return new StudentSimpleResponseApiDto(student.getId(), student.getPersonInformation().getFullName());
        }
        return null;
    }

    public static Student toEntity(StudentRequestApiDto dto) {
        return new Student(
                PersonInformationMapper.mapToPersonInformation(dto),
                AddressMapper.mapToAddress(dto)
        );
    }
}

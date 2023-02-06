package pl.edziennik.eDziennik.server.student.domain.dto.mapper;

import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.personinformation.PersonInformationMapper;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;

public class StudentMapper {


    private StudentMapper() {
    }

    public static StudentResponseApiDto toDto(Student entity) {
        return new StudentResponseApiDto(
                entity.getId(),
                entity.getUser().getUsername(),
                entity.getPersonInformation().getFirstName(),
                entity.getPersonInformation().getLastName(),
                entity.getAddress().getAddress(),
                entity.getAddress().getPostalCode(),
                entity.getAddress().getCity(),
                entity.getPersonInformation().getPesel(),
                entity.getParentFirstName(),
                entity.getParentLastName(),
                entity.getParentPhoneNumber(),
                entity.getUser().getEmail(),
                entity.getSchool().getId(),
                entity.getSchoolClass().getId()
        );
    }

    public static Student toEntity(StudentRequestApiDto dto) {
        return new Student(
                dto.getParentFirstName(),
                dto.getParentLastName(),
                dto.getParentPhoneNumber(),
                PersonInformationMapper.mapToPersonInformation(dto.getFirstName(), dto.getLastName(), dto.getPesel()),
                AddressMapper.mapToAddress(dto.getAddress(), dto.getCity(), dto.getPostalCode())
        );
    }
}

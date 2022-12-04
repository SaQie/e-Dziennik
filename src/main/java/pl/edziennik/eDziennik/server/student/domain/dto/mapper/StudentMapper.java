package pl.edziennik.eDziennik.server.student.domain.dto.mapper;

import pl.edziennik.eDziennik.server.address.AddressMapper;
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
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAddress().getAddress(),
                entity.getAddress().getPostalCode(),
                entity.getAddress().getCity(),
                entity.getPESEL(),
                entity.getParentFirstName(),
                entity.getParentLastName(),
                entity.getParentPhoneNumber(),
                entity.getSchool().getId(),
                entity.getSchoolClass().getId()
        );
    }

    public static Student toEntity(StudentRequestApiDto dto) {
        return new Student(
                dto.getUsername(),
                dto.getPassword(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPesel(),
                dto.getParentFirstName(),
                dto.getParentLastName(),
                dto.getParentPhoneNumber(),
                AddressMapper.mapToAddress(dto.getAddress(),dto.getCity(),dto.getPostalCode())
        );
    }
}

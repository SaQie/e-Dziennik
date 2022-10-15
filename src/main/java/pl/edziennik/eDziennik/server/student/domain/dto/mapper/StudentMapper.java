package pl.edziennik.eDziennik.server.student.domain.dto.mapper;

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
                entity.getAdress(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getPESEL(),
                entity.getParentFirstName(),
                entity.getParentLastName(),
                entity.getParentPhoneNumber(),
                SchoolMapper.toDto(entity.getSchool()),
                SchoolClassMapper.toDto(entity.getSchoolClass())
        );
    }

    public static Student toEntity(StudentRequestApiDto dto) {
        return new Student(
                dto.getAdress(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPostalCode(),
                dto.getPesel(),
                dto.getCity(),
                dto.getParentFirstName(),
                dto.getParentLastName(),
                dto.getParentPhoneNumber()
        );
    }
}

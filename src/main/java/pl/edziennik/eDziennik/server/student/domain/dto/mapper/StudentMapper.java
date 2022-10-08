package pl.edziennik.eDziennik.server.student.domain.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.basics.AbstractMapper;
import pl.edziennik.eDziennik.server.student.domain.Student;

@Component
@AllArgsConstructor
public class StudentMapper implements AbstractMapper<StudentResponseApiDto, Student> {

    private final SchoolMapper schoolMapper;
    private final SchoolClassMapper schoolClassMapper;

    @Override
    public StudentResponseApiDto toDto(Student entity) {
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
                schoolMapper.toDto(entity.getSchool()),
                schoolClassMapper.toDto(entity.getSchoolClass())
        );
    }

    @Override
    public Student toEntity(StudentResponseApiDto dto) {
        return null;
    }

    public Student toEntity(StudentRequestApiDto dto){
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

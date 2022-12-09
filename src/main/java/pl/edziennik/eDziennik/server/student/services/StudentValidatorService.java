package pl.edziennik.eDziennik.server.student.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.server.student.services.validator.StudentValidators;

@Service
@AllArgsConstructor
public class StudentValidatorService extends ServiceValidator<StudentValidators, StudentRequestApiDto> {

    private final SchoolDao dao;

    protected Student validateDtoAndMapToEntity(StudentRequestApiDto dto){
        super.validate(dto);
        Student student = StudentMapper.toEntity(dto);
        School school = dao.get(dto.getIdSchool());
        SchoolClass schoolClass = dao.get(SchoolClass.class, dto.getIdSchoolClass());
        student.setSchool(school);
        student.setSchoolClass(schoolClass);
        return student;
    }

    protected void validateDto(StudentRequestApiDto requestApiDto) {
        super.validate(requestApiDto);
    }


}

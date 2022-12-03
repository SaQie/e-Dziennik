package pl.edziennik.eDziennik.server.student.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.server.student.services.validator.StudentValidators;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class StudentPrivService extends ServiceValidator<StudentValidators, StudentRequestApiDto> {

    private final SchoolDao dao;

    protected Student validateDtoAndMapToEntity(StudentRequestApiDto dto){
        super.validate(dto);
        Student student = StudentMapper.toEntity(dto);
        checkSchoolExistAndAssignIfExist(dto.getIdSchool(), student);
        checkSchoolClassExistAndAssignIfExist(dto.getIdSchoolClass(), student);
        return student;
    }

    protected void validateDto(StudentRequestApiDto requestApiDto) {
        super.validate(requestApiDto);
    }

    private void checkSchoolExistAndAssignIfExist(Long idSchool, Student student) {
        dao.findWithExecute(idSchool, student::setSchool);
    }

    private void checkSchoolClassExistAndAssignIfExist(Long idSchoolClass, Student student) {
        dao.findWithExecute(SchoolClass.class, idSchoolClass, student::setSchoolClass);
    }


}

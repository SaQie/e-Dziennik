package pl.edziennik.eDziennik.server.subject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.mapper.SubjectMapper;
import pl.edziennik.eDziennik.server.subject.services.validator.SubjectValidators;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

@Service
@AllArgsConstructor
public class SubjectValidatorService extends ServiceValidator<SubjectValidators, SubjectRequestApiDto> {

    private final TeacherDao dao;

    protected Subject validateDtoAndMapToEntity(SubjectRequestApiDto dto){
        super.validate(dto);
        Subject subject = SubjectMapper.toEntity(dto);
        if (dto.getIdTeacher() != null){
            dao.findWithExecute(Teacher.class, dto.getIdTeacher(), subject::setTeacher);
        }
        SchoolClass schoolClass = dao.get(SchoolClass.class, dto.getIdSchoolClass());
        schoolClass.addSubject(subject);
        return subject;
    }


}

package pl.edziennik.eDziennik.server.schoolclass.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.server.schoolclass.services.validator.SchoolClassValidators;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

@Service
@AllArgsConstructor
public class SchoolClassValidatorService extends ServiceValidator<SchoolClassValidators, SchoolClassRequestApiDto> {

    private final SchoolDao dao;

    protected SchoolClass validateDtoAndMapToEntity(SchoolClassRequestApiDto dto){
        super.validate(dto);
        SchoolClass schoolClass = SchoolClassMapper.toEntity(dto);
        School school = dao.get(School.class, dto.getIdSchool());
        Teacher teacher = null;
        if (dto.getIdSupervisingTeacher() != null) {
            teacher = dao.get(Teacher.class, dto.getIdSupervisingTeacher());
        }
        schoolClass.setSchool(school);
        schoolClass.setTeacher(teacher);
        return schoolClass;
    }

}

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
class SubjectValidatorService extends ServiceValidator<SubjectValidators, SubjectRequestApiDto> {

    @Override
    protected void valid(SubjectRequestApiDto dto) {
        runValidatorChain(dto);
    }
}

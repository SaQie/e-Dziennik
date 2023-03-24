package pl.edziennik.eDziennik.domain.subject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.services.validator.SubjectValidators;

@Service
@AllArgsConstructor
class SubjectValidatorService extends ServiceValidator<SubjectValidators, SubjectRequestApiDto> {

    protected void valid(SubjectRequestApiDto dto) {
        runValidators(dto);

    }
}

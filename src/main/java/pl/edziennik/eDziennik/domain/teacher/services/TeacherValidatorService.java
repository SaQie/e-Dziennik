package pl.edziennik.eDziennik.domain.teacher.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.services.validator.TeacherValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;

@Service
@AllArgsConstructor
class TeacherValidatorService extends ServiceValidator<TeacherValidators, TeacherRequestApiDto> {

    protected void validate(TeacherRequestApiDto dto) {
        runValidators(dto);
    }

}

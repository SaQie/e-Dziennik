package pl.edziennik.eDziennik.domain.studentsubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.basic.validator.ServiceValidator;

@Service
@AllArgsConstructor
class StudentSubjectValidatorService extends ServiceValidator<StudentSubjectRequestDto> {


    protected void valid(final StudentSubjectRequestDto dto) {
        validate(dto);
    }

}

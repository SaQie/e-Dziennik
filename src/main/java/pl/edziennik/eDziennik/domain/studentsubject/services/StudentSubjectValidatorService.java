package pl.edziennik.eDziennik.domain.studentsubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.services.validator.StudentSubjectValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;

@Service
@AllArgsConstructor
class StudentSubjectValidatorService extends ServiceValidator<StudentSubjectValidators, StudentSubjectRequestDto> {


    protected void valid(final StudentSubjectRequestDto dto) {
        runValidators(dto);
    }

}

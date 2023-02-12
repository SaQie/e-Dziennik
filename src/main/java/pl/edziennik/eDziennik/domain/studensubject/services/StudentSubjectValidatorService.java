package pl.edziennik.eDziennik.domain.studensubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.studensubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;
import pl.edziennik.eDziennik.domain.studensubject.services.validator.StudentSubjectValidators;

@Service
@AllArgsConstructor
class StudentSubjectValidatorService extends ServiceValidator<StudentSubjectValidators, StudentSubjectRequestDto> {

    @Override
    protected void valid(StudentSubjectRequestDto dto) {
        runValidatorChain(dto);
        basicValidator.checkStudentExist(dto.getIdStudent());
        basicValidator.checkSubjectExist(dto.getIdSubject());
    }

}

package pl.edziennik.eDziennik.domain.studentsubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;
import pl.edziennik.eDziennik.domain.studentsubject.services.validator.StudentSubjectValidators;

@Service
@AllArgsConstructor
class StudentSubjectValidatorService extends ServiceValidator<StudentSubjectValidators, StudentSubjectRequestDto> {


    protected void valid(StudentSubjectRequestDto dto) {
        runValidators(dto);
        basicValidator.checkStudentExist(dto.getIdStudent());
        basicValidator.checkSubjectExist(dto.getIdSubject());
    }

}

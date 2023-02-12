package pl.edziennik.eDziennik.domain.student.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.services.validator.StudentValidators;

@Service
@AllArgsConstructor
class StudentValidatorService extends ServiceValidator<StudentValidators, StudentRequestApiDto> {

    @Override
    protected void valid(StudentRequestApiDto dto) {
        runValidatorChain(dto);
        basicValidator.checkSchoolClassExist(dto.getIdSchoolClass());
        basicValidator.checkSchoolExist(dto.getIdSchool());
    }
}

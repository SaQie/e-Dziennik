package pl.edziennik.eDziennik.domain.student.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.student.dao.StudentDao;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.services.validator.StudentValidators;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;

@Service
@AllArgsConstructor
class StudentValidatorService extends ServiceValidator<StudentValidators, StudentRequestApiDto> {


    protected void valid(StudentRequestApiDto dto) {
        runValidators(dto, ValidatePurpose.CREATE);
        basicValidator.checkSchoolClassExist(dto.getIdSchoolClass());
        basicValidator.checkSchoolExist(dto.getIdSchool());
    }
}

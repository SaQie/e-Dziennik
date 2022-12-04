package pl.edziennik.eDziennik.server.student.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

@Component
@AllArgsConstructor
public class StudentSchoolClassBelongsToSchoolValidator  implements StudentValidators{

    private final SchoolDao dao;
    private final ResourceCreator resourceCreator;

    private static final Integer VALIDATOR_ID = 2;

    @Override
    public String getValidatorName() {
        return StudentSchoolClassBelongsToSchoolValidator.class.getName();
    }

    @Override
    public Integer getValidationNumber() {
        return VALIDATOR_ID;
    }

    @Override
    public ValidatorPriority getValidationPriority() {
        return ValidatorPriority.HIGH;
    }

    @Override
    public Optional<ApiErrorsDto> validate(StudentRequestApiDto dto) {
        School school = dao.get(dto.getIdSchool());
        SchoolClass schoolClass = dao.get(SchoolClass.class, dto.getIdSchoolClass());
        if (!school.getSchoolClasses().contains(schoolClass)){
            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL, schoolClass.getClassName(), school.getName());
            ApiErrorsDto error = new ApiErrorsDto(StudentRequestApiDto.ID_SCHOOL_CLASS, message, false, getValidatorName(), ExceptionType.BUSINESS);
            return Optional.of(error);
        }
        return Optional.empty();
    }
}

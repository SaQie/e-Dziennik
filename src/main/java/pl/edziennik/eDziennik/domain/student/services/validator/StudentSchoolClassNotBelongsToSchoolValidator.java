package pl.edziennik.eDziennik.domain.student.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatorPriority;
import pl.edziennik.eDziennik.domain.school.dao.SchoolDao;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator for check that school class belongs to school when adding student
 */
@Component
@AllArgsConstructor
class StudentSchoolClassNotBelongsToSchoolValidator implements StudentValidators{

    private final SchoolDao dao;
    private final ResourceCreator resourceCreator;

    private static final Integer VALIDATOR_ID = 2;

    @Override
    public String getValidatorName() {
        return this.getClass().getSimpleName();
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
    public Optional<ApiErrorDto> validate(StudentRequestApiDto dto) {
        School school = dao.get(dto.getIdSchool());
        SchoolClass schoolClass = dao.get(SchoolClass.class, dto.getIdSchoolClass());
        if (!school.getSchoolClasses().contains(schoolClass)){
            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL, schoolClass.getClassName(), school.getName());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(StudentRequestApiDto.ID_SCHOOL_CLASS)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorName())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorDto);
        }
        return Optional.empty();
    }
}
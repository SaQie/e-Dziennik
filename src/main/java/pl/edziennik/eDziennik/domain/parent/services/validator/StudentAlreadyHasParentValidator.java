package pl.edziennik.eDziennik.domain.parent.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.parent.dao.ParentDao;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.basics.validator.ValidatorPriority;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that checks student already has parent
 */
@Component
@AllArgsConstructor
class StudentAlreadyHasParentValidator implements ParentValidators {

    private final ParentDao dao;
    private final ResourceCreator resourceCreator;

    public static final Integer VALIDATOR_ID = 1;

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
    public Optional<ApiErrorDto> validate(ParentRequestApiDto dto) {
        Student student = dao.get(Student.class, dto.getIdStudent());
        if (student.getParent() != null) {

            String studentFullName = student.getPersonInformation().getFullName();

            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_ALREADY_HAS_PARENT_VALIDATOR, studentFullName);

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(ParentRequestApiDto.ID_STUDENT)
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

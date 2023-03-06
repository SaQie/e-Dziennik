package pl.edziennik.eDziennik.domain.student.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.student.dao.StudentDao;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;
import java.util.Set;

/**
 * Validator that checks student still has parent when trying to delete student
 */
@Component
@AllArgsConstructor
class StudentStillHasParentValidator implements StudentValidators {

    private final StudentDao dao;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return STUDENT_STILL_HAS_PARENT_VALIDATOR;
    }

    @Override
    public Set<ValidatePurpose> getValidatorPurposes() {
        return Set.of(ValidatePurpose.DELETE);
    }

    @Override
    public Optional<ApiErrorDto> validate(StudentRequestApiDto dto) {
        Student student = dao.getByUsername(dto.getUsername());
        if (student.getParent() != null || student.isHasParentAccount()) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_STILL_HAS_PARENT, student.getPersonInformation().getFullName());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorId())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorDto);
        }


        return Optional.empty();

    }
}
package pl.edziennik.eDziennik.domain.parent.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that checks student already has parent
 */
@Component
@AllArgsConstructor
class StudentAlreadyHasParentValidator extends BaseService implements ParentValidators {

    private final StudentRepository repository;
    private final ResourceCreator resourceCreator;


    @Override
    public String getValidatorId() {
        return STUDENT_ALREADY_HAS_PARENT_VALIDATOR;
    }

    @Override
    public Optional<ApiValidationResult> validate(ParentRequestApiDto dto) {
        Student student = repository.findById(dto.studentId())
                .orElseThrow(notFoundException(dto.studentId().id(), Student.class));
        if (student.getParent() != null) {

            String studentFullName = student.getPersonInformation().fullName();

            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_ALREADY_HAS_PARENT_VALIDATOR, studentFullName);

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(ParentRequestApiDto.ID_STUDENT)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorId())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiValidationResult);

        }

        return Optional.empty();
    }
}

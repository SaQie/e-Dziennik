package pl.edziennik.eDziennik.domain.student.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator for already existing students pesel numbers
 */
@Component
@AllArgsConstructor
class StudentPeselNotUniqueValidator implements StudentValidators {

    private final StudentRepository repository;
    private final ResourceCreator resourceCreator;


    @Override
    public String getValidatorId() {
        return STUDENT_PESEL_NOT_UNIQUE_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(StudentRequestApiDto dto) {
        if (repository.isStudentExistsByPesel(dto.getPesel(), Role.RoleConst.ROLE_STUDENT.getId())) {
            String message = resourceCreator.of(EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, dto.getPesel());

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(StudentRequestApiDto.PESEL)
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

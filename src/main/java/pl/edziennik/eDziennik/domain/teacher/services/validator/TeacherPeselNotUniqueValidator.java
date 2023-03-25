package pl.edziennik.eDziennik.domain.teacher.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator for already existing teachers pesel numbers
 */
@Component
@AllArgsConstructor
class TeacherPeselNotUniqueValidator implements TeacherValidators {

    private final TeacherRepository repository;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return TEACHER_PESEL_NOT_UNIQUE_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(TeacherRequestApiDto dto) {
        if (repository.isTeacherExistsByPesel(dto.getPesel(), Role.RoleConst.ROLE_TEACHER.getId())) {
            String message = resourceCreator.of(EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, dto.getPesel());

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(TeacherRequestApiDto.PESEL)
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

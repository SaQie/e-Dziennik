package pl.edziennik.eDziennik.domain.schoolclass.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check, the school class already exist
 */
@Component
@AllArgsConstructor
class SchoolClassAlreadyExistsValidator extends BaseService implements SchoolClassValidators {

    private final SchoolClassRepository repository;
    private final SchoolRepository schoolRepository;

    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return SCHOOL_CLASS_ALREADY_EXISTS_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(SchoolClassRequestApiDto dto) {
        if (repository.existsByClassNameAndSchoolId(dto.className(), dto.schoolId().id())) {
            String schoolName = schoolRepository.findById(dto.schoolId())
                    .orElseThrow(notFoundException(dto.schoolId(), School.class)).getName();

            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST, dto.className(), schoolName);

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(SchoolClassRequestApiDto.CLASS_NAME)
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

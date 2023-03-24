package pl.edziennik.eDziennik.domain.subject.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.repository.SubjectRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator for already existing subject in school class
 */
@Component
@AllArgsConstructor
class SubjectAlreadyExistValidator extends BaseService implements SubjectValidators {

    private final SubjectRepository repository;
    private final SchoolClassRepository schoolClassRepository;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return SUBJECT_ALREADY_EXIST_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiErrorDto> validate(SubjectRequestApiDto dto) {
        if (repository.existsByNameAndSchoolClassId(dto.getName(), dto.getIdSchoolClass())) {
            SchoolClass schoolClass = schoolClassRepository.findById(dto.getIdSchoolClass())
                    .orElseThrow(notFoundException(dto.getIdSchoolClass(), SchoolClass.class));

            String message = resourceCreator.of(EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST, dto.getName(), schoolClass.getClassName());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(SubjectRequestApiDto.NAME)
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

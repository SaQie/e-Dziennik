package pl.edziennik.eDziennik.domain.subject.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.subject.dao.SubjectDao;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator for already existing subject in school class
 */
@Component
@AllArgsConstructor
class SubjectAlreadyExistValidator implements SubjectValidators {

    private final SubjectDao dao;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return SUBJECT_ALREADY_EXIST_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiErrorDto> validate(SubjectRequestApiDto dto) {
        if (dao.isSubjectAlreadyExist(dto.getName(), dto.getIdSchoolClass())) {
            SchoolClass schoolClass = dao.get(SchoolClass.class, dto.getIdSchoolClass());

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

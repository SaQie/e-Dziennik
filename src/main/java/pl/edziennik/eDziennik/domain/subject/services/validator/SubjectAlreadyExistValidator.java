package pl.edziennik.eDziennik.domain.subject.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatorPriority;
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
class SubjectAlreadyExistValidator implements SubjectValidators{

    private final SubjectDao dao;
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
    public Optional<ApiErrorDto> validate(SubjectRequestApiDto dto) {
        if (dao.isSubjectAlreadyExist(dto.getName(), dto.getIdSchoolClass())){
            SchoolClass schoolClass = dao.get(SchoolClass.class, dto.getIdSchoolClass());

            String message = resourceCreator.of(EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST, dto.getName(), schoolClass.getClassName());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(SubjectRequestApiDto.NAME)
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

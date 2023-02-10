package pl.edziennik.eDziennik.server.subject.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.subject.dao.SubjectDao;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.List;
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
    public Optional<ApiErrorsDto> validate(SubjectRequestApiDto dto) {
        if (dao.isSubjectAlreadyExist(dto.getName(), dto.getIdSchoolClass())){
            SchoolClass schoolClass = dao.get(SchoolClass.class, dto.getIdSchoolClass());

            String message = resourceCreator.of(EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST, dto.getName(), schoolClass.getClassName());

            ApiErrorsDto apiErrorsDto = ApiErrorsDto.builder()
                    .field(SubjectRequestApiDto.NAME)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorName())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorsDto);
        }
        return Optional.empty();
    }
}

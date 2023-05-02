package pl.edziennik.eDziennik.domain.student.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.basic.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.basic.service.BaseService;
import pl.edziennik.eDziennik.server.exception.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator for check that school class belongs to school when adding student
 */
@Component
@AllArgsConstructor
class StudentSchoolClassNotBelongsToSchoolValidator extends BaseService implements StudentValidators {

    private final SchoolRepository repository;
    private final SchoolClassRepository schoolClassRepository;

    private final ResourceCreator resourceCreator;


    @Override
    public String getValidatorId() {
        return STUDENT_SCHOOL_CLASS_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(StudentRequestApiDto dto) {
        School school = repository.findById(dto.schoolId())
                .orElseThrow(notFoundException(dto.schoolId(), School.class));
        SchoolClass schoolClass = schoolClassRepository.findById(dto.schoolClassId())
                .orElseThrow(notFoundException(dto.schoolClassId(), SchoolClass.class));

        if (!school.getSchoolClasses().contains(schoolClass)) {
            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL, schoolClass.getClassName(), school.getName());

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(StudentRequestApiDto.ID_SCHOOL_CLASS)
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

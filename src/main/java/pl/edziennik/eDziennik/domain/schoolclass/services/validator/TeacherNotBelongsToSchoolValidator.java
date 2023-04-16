package pl.edziennik.eDziennik.domain.schoolclass.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check, attributing teacher is from the same school
 */
@Component
@AllArgsConstructor
class TeacherNotBelongsToSchoolValidator extends BaseService implements SchoolClassValidators {

    private final SchoolClassRepository repository;
    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;

    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return TEACHER_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(SchoolClassRequestApiDto dto) {
        if (dto.idClassTeacher() != null) {
            if (!repository.existsByTeacherIdAndSchoolId(dto.idClassTeacher(), dto.schoolId())) {
                Teacher teacher = teacherRepository.findById(dto.idClassTeacher())
                        .orElseThrow(notFoundException(dto.idClassTeacher(), Teacher.class));

                String teacherName = teacher.getPersonInformation().fullName();

                String schoolName = schoolRepository.findById(dto.schoolId())
                        .orElseThrow(notFoundException(dto.schoolId(), School.class)).getName();

                String message = resourceCreator.of(EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL, teacherName, schoolName);

                ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                        .field(SchoolClassRequestApiDto.ID_CLASS_TEACHER)
                        .cause(message)
                        .thrownImmediately(false)
                        .errorThrownedBy(getValidatorId())
                        .exceptionType(ExceptionType.BUSINESS)
                        .build();

                return Optional.of(apiValidationResult);
            }
        }
        return Optional.empty();
    }
}

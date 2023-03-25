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
        if (dto.getIdClassTeacher() != null) {
            if (!repository.existsByTeacherIdAndSchoolId(dto.getIdClassTeacher(), dto.getIdSchool())) {
                Teacher teacher = teacherRepository.findById(dto.getIdClassTeacher())
                        .orElseThrow(notFoundException(dto.getIdClassTeacher(), Teacher.class));

                String teacherName = teacher.getPersonInformation().getFullName();

                String schoolName = schoolRepository.findById(dto.getIdSchool())
                        .orElseThrow(notFoundException(dto.getIdSchool(), School.class)).getName();

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

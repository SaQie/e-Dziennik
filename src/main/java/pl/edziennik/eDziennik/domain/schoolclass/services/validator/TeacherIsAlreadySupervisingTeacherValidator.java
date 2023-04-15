package pl.edziennik.eDziennik.domain.schoolclass.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
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
 * Validator that check, attributed teacher is already class master
 */
@Component
@AllArgsConstructor
class TeacherIsAlreadySupervisingTeacherValidator extends BaseService implements SchoolClassValidators {

    private final SchoolClassRepository repository;
    private final TeacherRepository teacherRepository;

    private final ResourceCreator resourceCreator;


    @Override
    public String getValidatorId() {
        return TEACHER_IS_ALREADY_SUPERVISING_TEACHER_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(SchoolClassRequestApiDto dto) {
        if (dto.idClassTeacher() != null) {
            if (repository.existsByTeacherId(dto.idClassTeacher().id())) {
                Teacher teacher = teacherRepository.findById(dto.idClassTeacher())
                        .orElseThrow(notFoundException(dto.idClassTeacher(), Teacher.class));
                String actualTeacherSchoolClassName = repository.getSchoolClassNameBySupervisingTeacherId(dto.idClassTeacher().id());

                String teacherName = teacher.getPersonInformation().fullName();
                String message = resourceCreator.of(EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER, teacherName, actualTeacherSchoolClassName);

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

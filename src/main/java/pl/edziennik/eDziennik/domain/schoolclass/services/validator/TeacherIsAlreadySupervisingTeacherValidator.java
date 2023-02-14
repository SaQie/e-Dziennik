package pl.edziennik.eDziennik.domain.schoolclass.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatorPriority;
import pl.edziennik.eDziennik.domain.schoolclass.dao.SchoolClassDao;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check, attributed teacher is already class master
 */
@Component
@AllArgsConstructor
class TeacherIsAlreadySupervisingTeacherValidator implements SchoolClassValidators {

    private final SchoolClassDao dao;
    private final ResourceCreator resourceCreator;

    public static final Integer VALIDATOR_ID = 2;

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
    public Optional<ApiErrorDto> validate(SchoolClassRequestApiDto dto) {
        if (dto.getIdClassTeacher() != null) {
            if (dao.isTeacherAlreadySupervisingTeacher(dto.getIdClassTeacher())) {
                Teacher teacher = dao.get(Teacher.class, dto.getIdClassTeacher());
                String actualTeacherSchoolClassName = dao.findSchoolClassNameBySupervisingTeacher(dto.getIdClassTeacher());

                String teacherName = teacher.getUser().getPersonInformation().getFirstName() + " " + teacher.getUser().getPersonInformation().getLastName();
                String message = resourceCreator.of(EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER, teacherName,actualTeacherSchoolClassName);

                ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                        .field(SchoolClassRequestApiDto.ID_CLASS_TEACHER)
                        .cause(message)
                        .thrownImmediately(false)
                        .errorThrownedBy(getValidatorName())
                        .exceptionType(ExceptionType.BUSINESS)
                        .build();

                return Optional.of(apiErrorDto);
            }
        }
        return Optional.empty();
    }
}

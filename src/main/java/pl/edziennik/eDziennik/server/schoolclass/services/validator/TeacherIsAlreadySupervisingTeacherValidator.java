package pl.edziennik.eDziennik.server.schoolclass.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.dao.SchoolClassDao;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.List;
import java.util.Optional;

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
    public Optional<ApiErrorsDto> validate(SchoolClassRequestApiDto dto) {
        if (dto.getIdSupervisingTeacher() != null) {
            if (dao.isTeacherAlreadySupervisingTeacher(dto.getIdSupervisingTeacher())) {
                Teacher teacher = dao.get(Teacher.class, dto.getIdSupervisingTeacher());

                String teacherName = teacher.getFirstName() + " " + teacher.getLastName();
                String message = resourceCreator.of(EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER, teacherName);

                ApiErrorsDto apiErrorsDto = ApiErrorsDto.builder()
                        .fields(List.of(SchoolClassRequestApiDto.ID_SUPERVISING_TEACHER))
                        .cause(message)
                        .thrownImmediately(false)
                        .errorThrownedBy(getValidatorName())
                        .exceptionType(ExceptionType.BUSINESS)
                        .build();

                return Optional.of(apiErrorsDto);
            }
        }
        return Optional.empty();
    }
}

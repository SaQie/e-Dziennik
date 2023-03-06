package pl.edziennik.eDziennik.domain.schoolclass.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.schoolclass.dao.SchoolClassDao;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check, attributing teacher is from the same school
 */
@Component
@AllArgsConstructor
class TeacherNotBelongsToSchoolValidator implements SchoolClassValidators {

    private final SchoolClassDao dao;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return TEACHER_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiErrorDto> validate(SchoolClassRequestApiDto dto) {
        if (dto.getIdClassTeacher() != null) {
            if (!dao.isTeacherBelongsToSchool(dto.getIdClassTeacher(), dto.getIdSchool())) {
                Teacher teacher = dao.get(Teacher.class, dto.getIdClassTeacher());

                String teacherName = teacher.getPersonInformation().getFirstName() + " " + teacher.getPersonInformation().getLastName();
                String schoolName = dao.get(School.class, dto.getIdSchool()).getName();
                String message = resourceCreator.of(EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL, teacherName, schoolName);

                ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                        .field(SchoolClassRequestApiDto.ID_CLASS_TEACHER)
                        .cause(message)
                        .thrownImmediately(false)
                        .errorThrownedBy(getValidatorId())
                        .exceptionType(ExceptionType.BUSINESS)
                        .build();

                return Optional.of(apiErrorDto);
            }
        }
        return Optional.empty();
    }
}

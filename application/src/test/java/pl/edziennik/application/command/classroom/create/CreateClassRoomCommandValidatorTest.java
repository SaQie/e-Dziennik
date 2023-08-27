package pl.edziennik.application.command.classroom.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateClassRoomCommandValidatorTest extends BaseUnitTest {

    private final CreateClassRoomCommandValidator validator;

    public CreateClassRoomCommandValidatorTest() {
        this.validator = new CreateClassRoomCommandValidator(schoolCommandRepository, classRoomCommandRepository);
    }

    @Test
    public void shouldThrowExceptionIfSchoolNotExists() {
        // given
        CreateClassRoomCommand command = new CreateClassRoomCommand(SchoolId.create(), ClassRoomName.of("TEST"));

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(CreateClassRoomCommand.SCHOOL_ID));
    }

    @Test
    public void shouldAddErrorWhenClassRoomNameAlreadyExists() {
        // given
        School school = createSchool("TEST", "123123", "123123", address);
        school = schoolCommandRepository.save(school);
        createClassRoom("122A", school);

        CreateClassRoomCommand command = new CreateClassRoomCommand(school.schoolId(), ClassRoomName.of("122A"));

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(CreateClassRoomCommand.CLASS_ROOM_NAME, errors.get(0).field());
        assertEquals(CreateClassRoomCommandValidator.CLASS_ROOM_NAME_ALREADY_EXISTS_MESSAGE_KEY, errors.get(0).message());
    }
}

package pl.edziennik.application.command.classroom.changename;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeClassRoomNameCommandValidatorTest extends BaseUnitTest {

    private final ChangeClassRoomNameCommandValidator validator;

    public ChangeClassRoomNameCommandValidatorTest() {
        this.validator = new ChangeClassRoomNameCommandValidator(classRoomCommandRepository);
    }

    @Test
    public void shouldThrowExceptionIfClassRoomNotExists() {
        // given
        ChangeClassRoomNameCommand command = new ChangeClassRoomNameCommand(ClassRoomId.create(), ClassRoomName.of("TEST"));


        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(ChangeClassRoomNameCommand.CLASS_ROOM_ID));

    }

    @Test
    public void shouldAddErrorWhenNewNameIsEqualToOldName() {
        // given
        School school = createSchool("TEST", "123123", "123123", address);
        ClassRoom classRoom = createClassRoom("122A", school);

        ClassRoomName newName = ClassRoomName.of("122A");
        ChangeClassRoomNameCommand command = new ChangeClassRoomNameCommand(classRoom.classRoomId(), newName);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(2, errors.size());

        assertEquals(errors.get(0).field(), ChangeClassRoomNameCommand.CLASS_ROOM_NAME);
        assertEquals(errors.get(0).message(), ChangeClassRoomNameCommandValidator.CLASS_ROOM_NAME_EQUAL_TO_OLD_MESSAGE_KEY);

        assertEquals(errors.get(1).field(), ChangeClassRoomNameCommand.CLASS_ROOM_NAME);
        assertEquals(errors.get(1).message(), ChangeClassRoomNameCommandValidator.CLASS_ROOM_NAME_ALREADY_EXISTS_MESSAGE_KEY);

    }

    @Test
    public void shouldAddErrorWhenClassRoomWithGivenNameAlreadyExists() {
        // given
        School school = createSchool("TEST", "123123", "123123", address);
        ClassRoom classRoom = createClassRoom("122A", school);
        createClassRoom("123A", school);

        ClassRoomName newName = ClassRoomName.of("123A");
        ChangeClassRoomNameCommand command = new ChangeClassRoomNameCommand(classRoom.classRoomId(), newName);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0).field(), ChangeClassRoomNameCommand.CLASS_ROOM_NAME);
        assertEquals(errors.get(0).message(), ChangeClassRoomNameCommandValidator.CLASS_ROOM_NAME_ALREADY_EXISTS_MESSAGE_KEY);
    }


}

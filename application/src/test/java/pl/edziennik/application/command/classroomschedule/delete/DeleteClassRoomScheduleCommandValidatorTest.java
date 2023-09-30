package pl.edziennik.application.command.classroomschedule.delete;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.command.teacherschedule.delete.DeleteTeacherScheduleCommand;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

public class DeleteClassRoomScheduleCommandValidatorTest extends BaseUnitTest {

    private final DeleteClassRoomScheduleCommandValidator validator;

    public DeleteClassRoomScheduleCommandValidatorTest() {
        this.validator = new DeleteClassRoomScheduleCommandValidator(classRoomScheduleCommandRepository);
    }

    @Test
    public void shouldThrowExceptionIfClassRoomScheduleNotExists(){
        // given
        DeleteClassRoomScheduleCommand command = new DeleteClassRoomScheduleCommand(ClassRoomScheduleId.create());

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(DeleteClassRoomScheduleCommand.CLASS_ROOM_SCHEDULE_ID));
    }
}

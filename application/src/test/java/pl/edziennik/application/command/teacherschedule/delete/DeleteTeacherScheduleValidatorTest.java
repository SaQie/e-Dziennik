package pl.edziennik.application.command.teacherschedule.delete;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.command.teacherschedule.create.CreateTeacherScheduleCommand;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

public class DeleteTeacherScheduleValidatorTest extends BaseUnitTest {

    private final DeleteTeacherScheduleCommandValidator validator;

    public DeleteTeacherScheduleValidatorTest() {
        this.validator = new DeleteTeacherScheduleCommandValidator(teacherScheduleCommandRepository);
    }

    @Test
    public void shouldThrowExceptionIfTeacherScheduleNotExists(){
        // given
        DeleteTeacherScheduleCommand command = new DeleteTeacherScheduleCommand(TeacherScheduleId.create());

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(DeleteTeacherScheduleCommand.TEACHER_SCHEDULE_ID));
    }


}

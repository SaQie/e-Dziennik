package pl.edziennik.application.integration.scheduler;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.teacher.create.CreateTeacherCommand;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.token.ActivationTokenRepository;
import pl.edziennik.infrastructure.scheduler.RemoveInactiveUserAccountsSchedulerTask;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

@DirtiesContext
public class RemoveInactiveUserAccountSchedulerTest extends BaseIntegrationTest {

    @Autowired
    private RemoveInactiveUserAccountsSchedulerTask task;

    @MockBean
    private ActivationTokenRepository activationTokenRepository;

    @Test
    @Transactional
    public void shouldRemoveInactiveUserAccount() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");

        CreateTeacherCommand command = new CreateTeacherCommand(
                Password.of("password"),
                Username.of("Test"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123123"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123000000"),
                schoolId
        );

        dispatcher.run(command);
        Teacher teacher = teacherCommandRepository.getByTeacherId(command.teacherId());
        List<UserId> userIds = List.of(teacher.user().userId());

        assertFalse(teacher.user().isActive());
        Mockito.when(activationTokenRepository.getUserIdsWithExpiredActivationTokens()).thenReturn(userIds);

        // when
        task.removeInactiveUserAccounts();

        // then
        teacher = teacherCommandRepository.getByTeacherId(command.teacherId());
        assertNull(teacher);
    }

}

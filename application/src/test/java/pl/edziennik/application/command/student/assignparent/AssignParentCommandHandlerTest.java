package pl.edziennik.application.command.student.assignparent;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssignParentCommandHandlerTest extends BaseUnitTest {

    private final AssignParentCommandHandler handler;

    public AssignParentCommandHandlerTest() {
        this.handler = new AssignParentCommandHandler(studentCommandRepository, parentCommandRepository);
    }

    @Test
    public void shouldAssignParentToStudent() {
        // given
        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudentWithSchoolAndClass(user, null);

        User parentUser = createUser("Test2", "Test2@example.com", RoleCommandMockRepo.PARENT_ROLE_NAME.value());
        Parent parent = createParent(parentUser, personInformation, address);
        parent = parentCommandRepository.save(parent);

        AssignParentCommand command = new AssignParentCommand(
                student.getStudentId(),
                parent.getParentId()
        );
        assertFalse(studentCommandRepository.hasAlreadyAssignedParent(student.getStudentId()));

        // when
        OperationResult operationResult = handler.handle(command);

        // then
        assertTrue(operationResult.isSuccess());
        assertTrue(studentCommandRepository.hasAlreadyAssignedParent(student.getStudentId()));

    }

}
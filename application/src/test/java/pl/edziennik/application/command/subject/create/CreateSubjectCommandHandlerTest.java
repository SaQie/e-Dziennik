package pl.edziennik.application.command.subject.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.teacher.Teacher;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateSubjectCommandHandlerTest extends BaseUnitTest {

    private final CreateSubjectCommandHandler handler;

    public CreateSubjectCommandHandlerTest() {
        this.handler = new CreateSubjectCommandHandler(subjectCommandRepository, schoolClassCommandRepository, teacherCommandRepository);
    }


    @Test
    public void shouldCreateSubject(){
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.school();
        Teacher teacher = schoolClass.teacher();

        CreateSubjectCommand command = new CreateSubjectCommand(
                Name.of("Przyroda"),
                Description.of("Test"),
                teacher.teacherId(),
                schoolClass.schoolClassId()
        );

        // when
        OperationResult operationResult = handler.handle(command);

        // then
        assertTrue(operationResult.isSuccess());
        assertNotNull(operationResult.identifier().id());
    }

}
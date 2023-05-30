package pl.edziennik.application.integration;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.grademanagment.assigngrade.AssignGradeToStudentSubjectCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.Description;
import pl.edziennik.common.valueobject.Weight;
import pl.edziennik.common.valueobject.id.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class GradeIntegrationTest extends BaseIntegrationTest {


    @Test
    @Transactional
    public void shouldAssignGradeToStudentSubject(){
        // given
        SchoolId schoolId = createSchool("Test", "123123", "1231233");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        SubjectId subjectId = createSubject("Test", schoolClassId, teacherId);
        StudentId studentId = createStudent("Test1", "Test1@example.com", "123123", schoolId, schoolClassId);

        StudentSubjectId studentSubjectId = assignStudentToSubject(studentId, subjectId);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                studentId,
                subjectId,
                Grade.FOUR,
                Weight.of(1),
                Description.of("Test"),
                teacherId
        );

        // when
        OperationResult operationResult = dispatcher.dispatch(command);

        // then
        pl.edziennik.domain.grade.Grade grade = gradeCommandRepository.getByGradeId(GradeId.of(operationResult.identifier().id()));
        assertNotNull(grade);
        assertEquals(grade.getStudentSubject().getStudentSubjectId(), studentSubjectId);
        assertEquals(grade.getTeacher().getTeacherId(), teacherId);
    }

}

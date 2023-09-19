package pl.edziennik.application.integration;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.schoolclass.create.CreateSchoolClassCommand;
import pl.edziennik.application.command.schoolclass.delete.DeleteSchoolClassCommand;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SchoolClassIntegrationTest extends BaseIntegrationTest {

    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    @Test
    public void shouldCreateSchoolClass() {
        // given
        SchoolId schoolId = createSchool("Test", "1231231", "123123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "1231233", schoolId);

        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("1A"),
                teacherId,
                schoolId
        );

        // when
        dispatcher.run(command);

        // then
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(command.schoolClassId());
        assertNotNull(schoolClass);
    }

    @Test
    public void shouldThrowExceptionIfSchoolClassAlreadyExistsInSchool() {
        // given
        SchoolId schoolId = createSchool("Testowa", "1231231", "1231231232");
        TeacherId teacherId = createTeacher("Nauczyciel", "test@example.com", "123123", schoolId);
        createSchoolClass(schoolId, teacherId, "1A");

        TeacherId secondTeacherId = createTeacher("Nauczyciel2", "Test@exampleee.com", "123123", schoolId);

        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("1A"),
                secondTeacherId,
                schoolId
        );

        try {
            // when
            dispatcher.run(command);
            Assertions.fail("Should throw exception when school class with given name exists in school");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateSchoolClassCommand.CLASS_NAME);
        }
    }

    @Test
    public void shouldDeleteSchoolClass() {
        // given
        SchoolId schoolId = createSchool("Testowa", "1231231", "1231231232");
        TeacherId teacherId = createTeacher("Nauczyciel", "test@example.com", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        assertOneRowExists("school_class");

        DeleteSchoolClassCommand command = new DeleteSchoolClassCommand(schoolClassId);

        // when
        dispatcher.run(command);

        // then
        assertNoOneRowExists("school_class");
    }

    @Test
    public void shouldThrowExceptionIfDeleteSchoolClassWithAssignedStudents() {
        // given
        SchoolId schoolId = createSchool("Testowa", "1231231", "1231231232");
        TeacherId teacherId = createTeacher("Nauczyciel", "test@example.com", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        assertOneRowExists("school_class");
        StudentId studentId = transactionTemplate.execute((x) -> createStudent("Test2", "test@examplee.com", "123123123", schoolId, schoolClassId));

        DeleteSchoolClassCommand command = new DeleteSchoolClassCommand(schoolClassId);

        try {
            // when
            dispatcher.run(command);
            Assertions.fail("Should throw exception if delete school class with assigned students");
        } catch (BusinessException e) {
            // then
            assertEquals(e.getErrors().size(), 1);
            assertEquals(e.getErrors().get(0).field(), DeleteSchoolClassCommand.SCHOOL_CLASS_ID);
            assertEquals(e.getErrors().get(0).errorCode(), ErrorCode.RELEATED_OBJECT_EXISTS.errorCode());
        }
    }

    @Test
    public void shouldThrowExceptionIfDeleteSchoolClassWithAssignedSubjects() {
        // given
        SchoolId schoolId = createSchool("Testowa", "1231231", "1231231232");
        TeacherId teacherId = createTeacher("Nauczyciel", "test@example.com", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        assertOneRowExists("school_class");
        transactionTemplate.execute((x) -> createSubject("Przyroda", schoolClassId,teacherId));

        DeleteSchoolClassCommand command = new DeleteSchoolClassCommand(schoolClassId);

        try {
            // when
            dispatcher.run(command);
            Assertions.fail("Should throw exception if delete school class with assigned subjects");
        } catch (BusinessException e) {
            // then
            assertEquals(e.getErrors().size(), 1);
            assertEquals(e.getErrors().get(0).field(), DeleteSchoolClassCommand.SCHOOL_CLASS_ID);
            assertEquals(e.getErrors().get(0).errorCode(), ErrorCode.RELEATED_OBJECT_EXISTS.errorCode());
        }
    }

}

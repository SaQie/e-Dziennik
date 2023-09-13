package pl.edziennik.application.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.subject.create.CreateSubjectCommand;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SubjectIntegrationTest extends BaseIntegrationTest {

    @Test
    @Transactional
    public void shouldCreateSubject() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Testowy", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        CreateSubjectCommand command = new CreateSubjectCommand(
                Name.of("Przyroda"),
                Description.of("Test"),
                teacherId,
                schoolClassId
        );

        // when
        dispatcher.run(command);

        // then
        Subject subject = subjectCommandRepository.getBySubjectId(command.subjectId());
        assertNotNull(subject);
        assertEquals(subject.schoolClass().schoolClassId(), schoolClassId);
    }

    @Test
    @Transactional
    public void shouldThrowExceptionWhenSubjectWithGivenNameAlreadyExistsInSchoolClass() {
        // given
        SchoolId schoolId = createSchool("Test", "1213123", "123123");
        TeacherId teacherId = createTeacher("Teacher", "adsasd@o2.pl", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        createSubject("Przyroda", schoolClassId, teacherId);
        TeacherId secondTeacherId = createTeacher("Teacher2", "qweqwe@o2.pl", "123122", schoolId);

        CreateSubjectCommand command = new CreateSubjectCommand(
                Name.of("Przyroda"),
                Description.of("Test"),
                secondTeacherId,
                schoolClassId
        );

        try {
            // when
            dispatcher.run(command);
            Assertions.fail("Should throw exception when subject with given name already exists in school class");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateSubjectCommand.NAME);
        }
    }

    @Test
    @Transactional
    public void shouldThrowExceptionWhenTeacherFromGivenSubjectIsNotFromTheSameSchoolAsSubject() {
        // given
        SchoolId schoolId = createSchool("Test", "1213123", "123123");
        SchoolId secondSchoolId = createSchool("Test2", "1235353", "3424234");

        TeacherId teacherId = createTeacher("Teacher", "adsasd@o2.pl", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        createSubject("Przyroda", schoolClassId, teacherId);
        TeacherId secondTeacherId = createTeacher("Teacher2", "qweqwe@o2.pl", "123122", secondSchoolId);

        CreateSubjectCommand command = new CreateSubjectCommand(
                Name.of("Przyroda1"),
                Description.of("Test"),
                secondTeacherId,
                schoolClassId
        );

        try {
            // when
            dispatcher.run(command);
            Assertions.fail("Should throw exception when given teacher is from another school than school class ");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateSubjectCommand.TEACHER_ID);
        }
    }


}

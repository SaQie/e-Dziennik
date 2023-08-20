package pl.edziennik.application.command.grademanagment.assigngrade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Weight;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;

/**
 * A command used for assigning grades to student's subject
 * <br>
 * Available grades see {@link Grade}
 */
@HandledBy(handler = AssignGradeToStudentSubjectCommandHandler.class)
@ValidatedBy(validator = AssignGradeToStudentSubjectCommandValidator.class)
public record AssignGradeToStudentSubjectCommand(

        @NotNull(message = "{student.empty}") StudentId studentId,
        @NotNull(message = "{subject.empty}") SubjectId subjectId,
        @NotNull(message = "{grade.empty}") Grade grade,
        @Valid @NotNull(message = "{weight.empty}") Weight weight,
        Description description,
        @NotNull(message = "{teacher.empty}") TeacherId teacherId


) implements ICommand<OperationResult> {

    public static final String STUDENT_ID = "studentId";
    public static final String SUBJECT_ID = "subjectId";
    public static final String GRADE = "grade";
    public static final String WEIGHT = "weight";
    public static final String DESCRIPTION = "description";
    public static final String TEACHER_ID = "teacherId";

}

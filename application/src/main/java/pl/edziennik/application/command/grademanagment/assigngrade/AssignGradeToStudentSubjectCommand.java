package pl.edziennik.application.command.grademanagment.assigngrade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Weight;

/**
 * A command used for assigning grades to student's subject
 * <br>
 * Available grades see {@link Grade}
 */
@Handler(handler = AssignGradeToStudentSubjectCommandHandler.class, validator = AssignGradeToStudentSubjectCommandValidator.class)
public record AssignGradeToStudentSubjectCommand(

        @JsonIgnore GradeId gradeId,
        @NotNull(message = "{student.empty}") StudentId studentId,
        @NotNull(message = "{subject.empty}") SubjectId subjectId,
        @NotNull(message = "{grade.empty}") Grade grade,
        @Valid @NotNull(message = "{weight.empty}") Weight weight,
        Description description,
        @NotNull(message = "{teacher.empty}") TeacherId teacherId


) implements Command {

    public static final String STUDENT_ID = "studentId";
    public static final String SUBJECT_ID = "subjectId";
    public static final String GRADE = "grade";
    public static final String WEIGHT = "weight";
    public static final String DESCRIPTION = "description";
    public static final String TEACHER_ID = "teacherId";


    @JsonCreator
    public AssignGradeToStudentSubjectCommand(StudentId studentId, SubjectId subjectId, Grade grade, Weight weight,
                                              Description description, TeacherId teacherId) {
        this(GradeId.create(), studentId, subjectId, grade, weight, description, teacherId);
    }
}

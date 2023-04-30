package pl.edziennik.eDziennik.domain.studentsubject.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;

import java.util.List;

@Getter
public class AllStudentsGradesInSubjectsDto {

    @JsonUnwrapped
    private final StudentId studentId;

    private final String firstName;
    private final String lastName;

    private final List<SubjectGradesResponseDto> subjects;

    public AllStudentsGradesInSubjectsDto(StudentId studentId, String firstName, String lastName, List<SubjectGradesResponseDto> subjects) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjects = subjects;
    }
}

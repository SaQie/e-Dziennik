package pl.edziennik.eDziennik.domain.studentsubject.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;

@Builder
public record StudentGradesInSubjectResponseApiDto(
        @JsonUnwrapped
        StudentId studentId,
        String fullName,
        SubjectGradesResponseDto subjectData
) {


}

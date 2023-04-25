package pl.edziennik.eDziennik.domain.studentsubject.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;


@Builder
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class StudentSubjectRequestDto {
    @NotNull(message = "{subject.empty}")
    private final SubjectId subjectId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final StudentId studentId;


    public static final String ID_SUBJECT = "idSubject";
    public static final String ID_STUDENT = "idStudent";


}

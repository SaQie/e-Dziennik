package pl.edziennik.eDziennik.domain.subject.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherSimpleResponseApiDto;


@Builder
public record SubjectResponseApiDto(
        @JsonUnwrapped
        SubjectId subjectId,
        String name,
        String description,
        SchoolClassSimpleResponseApiDto schoolClass,
        TeacherSimpleResponseApiDto teacher
) {
}

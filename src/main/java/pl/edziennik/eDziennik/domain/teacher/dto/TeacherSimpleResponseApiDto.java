package pl.edziennik.eDziennik.domain.teacher.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;

@Builder
public record TeacherSimpleResponseApiDto(
        @JsonUnwrapped
        TeacherId teacherId,
        String fullName
) {

}

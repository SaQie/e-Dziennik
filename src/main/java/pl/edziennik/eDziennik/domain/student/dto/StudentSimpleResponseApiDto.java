package pl.edziennik.eDziennik.domain.student.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;

@Builder
public record StudentSimpleResponseApiDto(
        @JsonUnwrapped
        StudentId studentId,
        String fullName
) {

}

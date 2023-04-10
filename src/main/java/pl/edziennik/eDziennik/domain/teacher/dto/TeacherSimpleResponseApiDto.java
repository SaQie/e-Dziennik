package pl.edziennik.eDziennik.domain.teacher.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record TeacherSimpleResponseApiDto(
        Long id,
        String fullName
) {

}

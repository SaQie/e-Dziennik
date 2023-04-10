package pl.edziennik.eDziennik.domain.subject.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherSimpleResponseApiDto;


@Builder
public record SubjectResponseApiDto(
        Long id,
        String name,
        String description,
        SchoolClassSimpleResponseApiDto schoolClass,
        TeacherSimpleResponseApiDto teacher
) {
}

package pl.edziennik.eDziennik.domain.schoolclass.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherSimpleResponseApiDto;

@Builder
public record SchoolClassResponseApiDto(
        Long id,
        String className,
        SchoolSimpleResponseApiDto school,
        TeacherSimpleResponseApiDto supervisingTeacher
) {

}

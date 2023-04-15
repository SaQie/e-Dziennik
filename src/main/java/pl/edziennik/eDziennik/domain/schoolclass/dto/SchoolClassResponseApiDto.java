package pl.edziennik.eDziennik.domain.schoolclass.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherSimpleResponseApiDto;

@Builder
public record SchoolClassResponseApiDto(
        @JsonUnwrapped
        SchoolClassId schoolClassId,
        String className,
        SchoolSimpleResponseApiDto school,
        TeacherSimpleResponseApiDto supervisingTeacher
) {

}

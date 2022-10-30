package pl.edziennik.eDziennik.server.schoolclass.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;

@Getter
public class SchoolClassResponseApiDto {

    private final Long id;
    private final String className;
    private final Long idSupervisingTeacher;
    private final Long idSchool;

    public SchoolClassResponseApiDto(Long id, String className, Long idSchool, Long idSupervisingTeacher) {
        this.id = id;
        this.className = className;
        this.idSupervisingTeacher = idSupervisingTeacher;
        this.idSchool = idSchool;
    }
}

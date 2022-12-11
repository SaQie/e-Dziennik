package pl.edziennik.eDziennik.server.subject.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;

@Getter
public class SubjectResponseApiDto {

    private final Long id;
    private final String name;
    private final String description;
    private final Long idSchoolClass;
    private final Long idTeacher;

    public SubjectResponseApiDto(Long id, String name, String description, Long idTeacher, Long idSchoolClass) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.idTeacher = idTeacher;
        this.idSchoolClass = idSchoolClass;
    }

    public SubjectResponseApiDto(Long id, String name, String description, Long idSchoolClass) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.idTeacher = null;
        this.idSchoolClass = idSchoolClass;
    }
}

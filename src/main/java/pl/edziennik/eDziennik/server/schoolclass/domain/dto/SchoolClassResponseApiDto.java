package pl.edziennik.eDziennik.server.schoolclass.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;

@Getter
public class SchoolClassResponseApiDto{

    private Long id;
    private String className;
    private TeacherResponseApiDto supervisingTeacher;
    private SchoolResponseApiDto school;

    public SchoolClassResponseApiDto(Long id, String className, TeacherResponseApiDto supervisingTeacher, SchoolResponseApiDto school) {
        this.id = id;
        this.className = className;
        this.supervisingTeacher = supervisingTeacher;
        this.school = school;
    }
}

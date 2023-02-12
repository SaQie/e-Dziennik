package pl.edziennik.eDziennik.domain.schoolclass.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherSimpleResponseApiDto;

@Getter
public class SchoolClassResponseApiDto {

    private final Long id;
    private final String className;
    private final TeacherSimpleResponseApiDto supervisingTeacher;
    private final SchoolSimpleResponseApiDto school;

    public SchoolClassResponseApiDto(Long id, String className, SchoolSimpleResponseApiDto school, TeacherSimpleResponseApiDto supervisingTeacher) {
        this.id = id;
        this.className = className;
        this.supervisingTeacher = supervisingTeacher;
        this.school = school;
    }
}

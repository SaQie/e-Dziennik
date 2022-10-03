package pl.edziennik.eDziennik.dto.schoolclass;

import lombok.Getter;
import pl.edziennik.eDziennik.dto.teacher.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.basics.AbstractDto;

@Getter
public class SchoolClassResponseApiDto extends AbstractDto {

    private Long id;
    private String className;
    private TeacherResponseApiDto supervisingTeacher;

    public SchoolClassResponseApiDto(Long id, String className, TeacherResponseApiDto supervisingTeacher) {
        this.id = id;
        this.className = className;
        this.supervisingTeacher = supervisingTeacher;
    }
}

package pl.edziennik.eDziennik.domain.schoolclass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchoolClassRequestApiDto{

    public static final String CLASS_NAME = "className";
    public static final String ID_CLASS_TEACHER = "idClassTeacher";
    public static final String ID_SCHOOL = "idSchool";

    @NotEmpty(message = "{className.empty}")
    private String className;

    private Long idClassTeacher;

    @NotNull(message = "{school.empty}")
    private Long idSchool;
}

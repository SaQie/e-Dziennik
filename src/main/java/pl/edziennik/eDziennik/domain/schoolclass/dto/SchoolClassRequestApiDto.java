package pl.edziennik.eDziennik.domain.schoolclass.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public record SchoolClassRequestApiDto(
        @NotEmpty(message = "{className.empty}")
        String className,

        Long idClassTeacher,

        @NotNull(message = "{school.empty}")
        Long idSchool
) {

    public static final String CLASS_NAME = "className";
    public static final String ID_CLASS_TEACHER = "idClassTeacher";
    public static final String ID_SCHOOL = "idSchool";

}

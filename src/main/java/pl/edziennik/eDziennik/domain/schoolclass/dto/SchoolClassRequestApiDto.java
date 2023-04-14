package pl.edziennik.eDziennik.domain.schoolclass.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class SchoolClassRequestApiDto {

    @NotEmpty(message = "{className.empty}")
    private final String className;

    private final TeacherId idClassTeacher;

    @NotNull(message = "{school.empty}")
    private final SchoolId schoolId;


    public static final String CLASS_NAME = "className";
    public static final String ID_CLASS_TEACHER = "idClassTeacher";
    public static final String ID_SCHOOL = "idSchool";

}

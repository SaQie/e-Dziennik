package pl.edziennik.eDziennik.domain.grade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GradeRequestApiDto {

    public static final String GRADE = "grade";
    public static final String WEIGHT = "weight";
    public static final String DESCRIPTION = "description";
    public static final String TEACHER_NAME = "teacherName";

    @NotNull(message = "{grade.empty}")
    private int grade;

    @NotNull(message = "{weight.empty}")
    private int weight;

    private String description;

    public GradeRequestApiDto(int grade, int weight, String description) {
        this.grade = grade;
        this.weight = weight;
        this.description = description;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String teacherName;

    public void setTeacherName(String teacherName){
        this.teacherName = teacherName;
    }

}

package pl.edziennik.eDziennik.server.grade.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GradeRequestApiDto {

    private int grade;
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

package pl.edziennik.eDziennik.domain.grade.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GradeRequestApiDto {

    public static final String GRADE = "grade";
    public static final String WEIGHT = "weight";
    public static final String DESCRIPTION = "description";

    @NotNull(message = "{grade.empty}")
    private Integer grade;

    @NotNull(message = "{weight.empty}")
    private Integer weight;

    private String description;

    public GradeRequestApiDto(int grade, int weight, String description) {
        this.grade = grade;
        this.weight = weight;
        this.description = description;
    }


}

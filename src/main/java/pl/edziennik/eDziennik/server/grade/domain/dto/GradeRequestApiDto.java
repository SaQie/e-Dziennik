package pl.edziennik.eDziennik.server.grade.domain.dto;

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

}

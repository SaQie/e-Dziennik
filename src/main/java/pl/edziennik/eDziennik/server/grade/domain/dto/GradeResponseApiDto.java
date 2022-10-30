package pl.edziennik.eDziennik.server.grade.domain.dto;

import lombok.Getter;

@Getter
public class GradeResponseApiDto {

    private final Long id;

    private final int grade;
    private final int weight;
    private final String description;

    public GradeResponseApiDto(Long id, int grade, int weight, String description) {
        this.id = id;
        this.grade = grade;
        this.weight = weight;
        this.description = description;
    }
}

package pl.edziennik.eDziennik.domain.student.dto;

import lombok.Getter;

@Getter
public class StudentSimpleResponseApiDto {

    private Long id;
    private String fullName;

    public StudentSimpleResponseApiDto(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}

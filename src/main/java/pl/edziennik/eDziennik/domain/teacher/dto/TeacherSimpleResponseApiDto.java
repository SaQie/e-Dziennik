package pl.edziennik.eDziennik.domain.teacher.dto;

import lombok.Getter;

@Getter
public class TeacherSimpleResponseApiDto {

    private final Long id;
    private final String fullName;

    public TeacherSimpleResponseApiDto(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
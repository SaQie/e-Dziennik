package pl.edziennik.eDziennik.domain.schoolclass.dto;

import lombok.Getter;

@Getter
public class SchoolClassSimpleResponseApiDto {

    private final Long id;
    private final String className;

    public SchoolClassSimpleResponseApiDto(Long id, String className) {
        this.id = id;
        this.className = className;
    }
}

package pl.edziennik.eDziennik.domain.school.dto;

import lombok.Getter;

@Getter
public class SchoolSimpleResponseApiDto {

    private final Long id;
    private final String name;


    public SchoolSimpleResponseApiDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

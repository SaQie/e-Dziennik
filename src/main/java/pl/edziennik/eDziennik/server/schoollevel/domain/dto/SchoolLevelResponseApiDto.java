package pl.edziennik.eDziennik.server.schoollevel.domain.dto;

import lombok.Getter;

@Getter
public class SchoolLevelResponseApiDto {

    private final Long id;
    private final String name;

    public SchoolLevelResponseApiDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

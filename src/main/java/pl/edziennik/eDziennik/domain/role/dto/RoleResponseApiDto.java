package pl.edziennik.eDziennik.domain.role.dto;

import lombok.Getter;

@Getter
public class RoleResponseApiDto{

    private final Long id;
    private final String name;

    public RoleResponseApiDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

package pl.edziennik.eDziennik.server.role.domain.dto;

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

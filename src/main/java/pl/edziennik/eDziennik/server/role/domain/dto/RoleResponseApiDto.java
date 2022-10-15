package pl.edziennik.eDziennik.server.role.domain.dto;

import lombok.Getter;

@Getter
public class RoleResponseApiDto{

    private Long id;
    private String name;

    public RoleResponseApiDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

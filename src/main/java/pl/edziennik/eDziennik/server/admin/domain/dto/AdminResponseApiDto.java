package pl.edziennik.eDziennik.server.admin.domain.dto;

import lombok.Getter;

@Getter
public class AdminResponseApiDto {

    private final Long id;
    private final String username;
    private final String email;

    public AdminResponseApiDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}

package pl.edziennik.eDziennik.dto.role;

import lombok.Getter;
import pl.edziennik.eDziennik.server.basics.AbstractDto;

@Getter
public class RoleResponseApiDto extends AbstractDto {

    private Long id;
    private String name;

    public RoleResponseApiDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

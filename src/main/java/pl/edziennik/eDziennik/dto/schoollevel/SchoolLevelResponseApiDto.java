package pl.edziennik.eDziennik.dto.schoollevel;

import lombok.Getter;
import pl.edziennik.eDziennik.server.basics.AbstractDto;

@Getter
public class SchoolLevelResponseApiDto extends AbstractDto {

    private Long id;
    private String name;

    public SchoolLevelResponseApiDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

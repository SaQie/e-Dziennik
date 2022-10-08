package pl.edziennik.eDziennik.server.subject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.basics.AbstractDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubjectRequestApiDto extends AbstractDto {

    private String name;
    private String description;
    private Long teacher;
    private Long schoolClass;

}

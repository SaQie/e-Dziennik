package pl.edziennik.eDziennik.server.schoolclass.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.basics.AbstractDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchoolClassRequestApiDto extends AbstractDto {

    private Long id;

    private String className;
    private Long supervisingTeacherId;
    private Long school;
}

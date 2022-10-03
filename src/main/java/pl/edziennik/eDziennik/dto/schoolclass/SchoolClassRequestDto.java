package pl.edziennik.eDziennik.dto.schoolclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.basics.AbstractDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchoolClassRequestDto extends AbstractDto {

    private Long id;

    private String className;
    private Long supervisingTeacherId;
}

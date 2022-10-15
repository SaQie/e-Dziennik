package pl.edziennik.eDziennik.server.schoolclass.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchoolClassRequestApiDto{

    private Long id;

    private String className;
    private Long supervisingTeacherId;
    private Long school;
}

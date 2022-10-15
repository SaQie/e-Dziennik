package pl.edziennik.eDziennik.server.subject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubjectRequestApiDto{

    private String name;
    private String description;
    private Long teacher;
    private Long schoolClass;

}

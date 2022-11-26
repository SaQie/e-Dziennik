package pl.edziennik.eDziennik.server.schoollevel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchoolLevelRequestApiDto {

    public static final String ID = "id";
    public static final String NAME = "name";

    private Long id;
    private String name;

}

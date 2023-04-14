package pl.edziennik.eDziennik.domain.schoollevel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.domain.schoollevel.domain.wrapper.SchoolLevelId;

@Builder
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class SchoolLevelRequestApiDto {
    private final SchoolLevelId schoolLevelId;
    private final String name;

    public static final String ID = "id";
    public static final String NAME = "name";


}

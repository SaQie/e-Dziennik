package pl.edziennik.eDziennik.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.entities.SchoolLevel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SchoolLevelDto {

    private Long id;
    private String name;

    public static SchoolLevelDto dtoFrom(SchoolLevel schoolLevel){
        SchoolLevelDto dto = new SchoolLevelDto();
        dto.id = schoolLevel.getId();
        dto.name = schoolLevel.getName();
        return dto;
    }

    public static SchoolLevel dtoTo(SchoolLevelDto dto){
        return SchoolLevel.builder()
                .name(dto.name)
                .build();
    }



}

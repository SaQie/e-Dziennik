package pl.edziennik.eDziennik.domain.schoolclass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchoolClassAssignSubjectRequestApiDto {

    private Long idSchoolClass;
    private Long idSubject;

}

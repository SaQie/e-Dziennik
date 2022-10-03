package pl.edziennik.eDziennik.server.schoolclass;

import pl.edziennik.eDziennik.dto.schoolclass.SchoolClassRequestDto;
import pl.edziennik.eDziennik.dto.schoolclass.SchoolClassResponseApiDto;

public interface SchoolClassService {

    SchoolClassResponseApiDto createSchoolClass(final SchoolClassRequestDto dto);

}

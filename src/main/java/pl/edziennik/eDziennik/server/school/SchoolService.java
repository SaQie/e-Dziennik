package pl.edziennik.eDziennik.server.school;

import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;

import java.util.List;

public interface SchoolService {

    SchoolResponseApiDto createNewSchool(final SchoolRequestApiDto dto);

    SchoolResponseApiDto findSchoolById(final Long id);

    void deleteSchoolById(final Long id);

    List<SchoolResponseApiDto> findAllSchools();

}

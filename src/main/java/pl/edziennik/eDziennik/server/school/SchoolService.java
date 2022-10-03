package pl.edziennik.eDziennik.server.school;

import pl.edziennik.eDziennik.dto.school.SchoolRequestDto;
import pl.edziennik.eDziennik.dto.school.SchoolResponseApiDto;

import java.util.List;

public interface SchoolService {

    SchoolResponseApiDto createNewSchool(final SchoolRequestDto dto);

    SchoolResponseApiDto findSchoolById(final Long id);

    void deleteSchoolById(final Long id);

    List<SchoolResponseApiDto> findAllSchools();

}

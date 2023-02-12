package pl.edziennik.eDziennik.domain.school.services;

import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;

import java.util.List;

public interface SchoolService {

    SchoolResponseApiDto createNewSchool(final SchoolRequestApiDto dto);

    SchoolResponseApiDto findSchoolById(final Long id);

    void deleteSchoolById(final Long id);

    List<SchoolResponseApiDto> findAllSchools();

    SchoolResponseApiDto updateSchool(final Long id, final SchoolRequestApiDto dto);
}

package pl.edziennik.eDziennik.domain.school.services;

import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;

import java.util.List;

public interface SchoolService {

    SchoolResponseApiDto createNewSchool(final SchoolRequestApiDto dto);

    SchoolResponseApiDto findSchoolById(final Long id);

    void deleteSchoolById(final Long id);

    Page<List<SchoolResponseApiDto>> findAllSchools(PageRequest pageRequest);

    SchoolResponseApiDto updateSchool(final Long id, final SchoolRequestApiDto dto);
}

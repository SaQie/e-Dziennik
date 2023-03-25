package pl.edziennik.eDziennik.domain.school.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

public interface SchoolService {

    SchoolResponseApiDto createNewSchool(final SchoolRequestApiDto dto);

    SchoolResponseApiDto findSchoolById(final Long id);

    void deleteSchoolById(final Long id);

    PageDto<SchoolResponseApiDto> findAllSchools(Pageable pageable);

    SchoolResponseApiDto updateSchool(final Long id, final SchoolRequestApiDto dto);

}

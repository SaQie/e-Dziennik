package pl.edziennik.eDziennik.domain.school.services;

import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.basic.page.PageDto;

public interface SchoolService {

    SchoolResponseApiDto createNewSchool(final SchoolRequestApiDto dto);

    SchoolResponseApiDto findSchoolById(final SchoolId schoolId);

    void deleteSchoolById(final SchoolId schoolId);

    PageDto<SchoolResponseApiDto> findAllSchools(final Pageable pageable);

    SchoolResponseApiDto updateSchool(final SchoolId schoolId, final SchoolRequestApiDto dto);

}

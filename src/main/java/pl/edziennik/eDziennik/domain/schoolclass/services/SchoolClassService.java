package pl.edziennik.eDziennik.domain.schoolclass.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

public interface SchoolClassService {

    SchoolClassResponseApiDto createSchoolClass(final SchoolClassRequestApiDto dto);


    SchoolClassResponseApiDto findSchoolClassById(final Long id);

    void deleteSchoolClassById(final Long id);

    PageDto<SchoolClassResponseApiDto> findAllSchoolClasses(Pageable Pageable);
    SchoolClassResponseApiDto updateSchoolClass(final Long id, final SchoolClassRequestApiDto dto);
    PageDto<SchoolClassResponseApiDto> findSchoolClassesBySchoolId(Pageable pageable,Long schoolId);
}

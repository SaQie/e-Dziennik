package pl.edziennik.eDziennik.domain.schoolclass.services;

import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.basic.page.PageDto;

public interface SchoolClassService {

    SchoolClassResponseApiDto createSchoolClass(final SchoolClassRequestApiDto dto);


    SchoolClassResponseApiDto findSchoolClassById(final SchoolClassId schoolClassId);

    void deleteSchoolClassById(final SchoolClassId schoolClassId);

    PageDto<SchoolClassResponseApiDto> findAllSchoolClasses(final Pageable Pageable);
    SchoolClassResponseApiDto updateSchoolClass(final SchoolClassId schoolClassId, final SchoolClassRequestApiDto dto);
    PageDto<SchoolClassResponseApiDto> findSchoolClassesBySchoolId(final Pageable pageable,final SchoolId schoolId);
}

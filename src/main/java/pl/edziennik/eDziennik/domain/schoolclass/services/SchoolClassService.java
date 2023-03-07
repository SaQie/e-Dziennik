package pl.edziennik.eDziennik.domain.schoolclass.services;

import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;

import java.util.List;

public interface SchoolClassService {

    SchoolClassResponseApiDto createSchoolClass(final SchoolClassRequestApiDto dto);


    SchoolClassResponseApiDto findSchoolClassById(final Long id);

    void deleteSchoolClassById(final Long id);

    Page<List<SchoolClassResponseApiDto>> findAllSchoolClasses(PageRequest pageRequest);
    SchoolClassResponseApiDto updateSchoolClass(final Long id, final SchoolClassRequestApiDto dto);
    List<SchoolClassResponseApiDto> findSchoolClassesBySchoolId(Long schoolId);
}

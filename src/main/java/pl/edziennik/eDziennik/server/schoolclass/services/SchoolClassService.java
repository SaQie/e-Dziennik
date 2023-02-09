package pl.edziennik.eDziennik.server.schoolclass.services;

import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassAssignSubjectRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;

import java.util.List;

public interface SchoolClassService {

    SchoolClassResponseApiDto createSchoolClass(final SchoolClassRequestApiDto dto);


    SchoolClassResponseApiDto findSchoolClassById(final Long id);

    void deleteSchoolClassById(final Long id);

    List<SchoolClassResponseApiDto> findAllSchoolClasses();
    SchoolClassResponseApiDto updateSchoolClass(final Long id, final SchoolClassRequestApiDto dto);
    List<SchoolClassResponseApiDto> findSchoolClassesBySchoolId(Long schoolId);
}

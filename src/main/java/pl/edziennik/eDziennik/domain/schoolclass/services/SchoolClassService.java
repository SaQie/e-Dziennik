package pl.edziennik.eDziennik.domain.schoolclass.services;

import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;

import java.util.List;

public interface SchoolClassService {

    SchoolClassResponseApiDto createSchoolClass(final SchoolClassRequestApiDto dto);


    SchoolClassResponseApiDto findSchoolClassById(final Long id);

    void deleteSchoolClassById(final Long id);

    List<SchoolClassResponseApiDto> findAllSchoolClasses();
    SchoolClassResponseApiDto updateSchoolClass(final Long id, final SchoolClassRequestApiDto dto);
    List<SchoolClassResponseApiDto> findSchoolClassesBySchoolId(Long schoolId);
}

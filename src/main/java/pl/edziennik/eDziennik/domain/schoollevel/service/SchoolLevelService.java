package pl.edziennik.eDziennik.domain.schoollevel.service;

import pl.edziennik.eDziennik.domain.schoollevel.dto.SchoolLevelResponseApiDto;

import java.util.List;

public interface SchoolLevelService {

    List<SchoolLevelResponseApiDto> getSchoolLevelList();

}

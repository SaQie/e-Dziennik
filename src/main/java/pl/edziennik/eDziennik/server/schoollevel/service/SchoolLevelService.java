package pl.edziennik.eDziennik.server.schoollevel.service;

import pl.edziennik.eDziennik.server.schoollevel.domain.dto.SchoolLevelResponseApiDto;

import java.util.List;

public interface SchoolLevelService {

    List<SchoolLevelResponseApiDto> getSchoolLevelList();

}

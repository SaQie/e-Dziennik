package pl.edziennik.eDziennik.server.schoollevel.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.schoollevel.dao.SchoolLevelDao;
import pl.edziennik.eDziennik.server.schoollevel.domain.SchoolLevel;
import pl.edziennik.eDziennik.server.schoollevel.domain.dto.SchoolLevelResponseApiDto;
import pl.edziennik.eDziennik.server.schoollevel.domain.dto.mapper.SchoolLevelMapper;

import java.util.List;

@Service
@AllArgsConstructor
class SchoolLevelServiceImpl implements SchoolLevelService{

    private final SchoolLevelDao dao;


    @Override
    public List<SchoolLevelResponseApiDto> getSchoolLevelList() {
        List<SchoolLevel> allSchoolLevels = dao.findAll();
        return SchoolLevelMapper.toDto(allSchoolLevels);
    }
}

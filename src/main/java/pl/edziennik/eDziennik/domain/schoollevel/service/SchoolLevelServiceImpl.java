package pl.edziennik.eDziennik.domain.schoollevel.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.schoollevel.dto.SchoolLevelResponseApiDto;
import pl.edziennik.eDziennik.domain.schoollevel.dto.mapper.SchoolLevelMapper;
import pl.edziennik.eDziennik.domain.schoollevel.dao.SchoolLevelDao;
import pl.edziennik.eDziennik.domain.schoollevel.domain.SchoolLevel;

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

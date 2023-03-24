package pl.edziennik.eDziennik.domain.schoollevel.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.schoollevel.domain.SchoolLevel;
import pl.edziennik.eDziennik.domain.schoollevel.dto.SchoolLevelResponseApiDto;
import pl.edziennik.eDziennik.domain.schoollevel.dto.mapper.SchoolLevelMapper;
import pl.edziennik.eDziennik.domain.schoollevel.repository.SchoolLevelRepository;

import java.util.List;

@Service
@AllArgsConstructor
class SchoolLevelServiceImpl implements SchoolLevelService {

    private final SchoolLevelRepository repository;


    @Override
    public List<SchoolLevelResponseApiDto> getSchoolLevelList() {
        List<SchoolLevel> allSchoolLevels = repository.findAll();
        return SchoolLevelMapper.toDto(allSchoolLevels);
    }
}

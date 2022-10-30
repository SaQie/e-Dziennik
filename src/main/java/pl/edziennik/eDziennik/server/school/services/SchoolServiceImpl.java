package pl.edziennik.eDziennik.server.school.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.school.domain.School;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class SchoolServiceImpl implements SchoolService{

    private final SchoolDao dao;
    private final SchoolPrivService privService;

    @Override
    @Transactional
    public SchoolResponseApiDto createNewSchool(SchoolRequestApiDto dto) {
        School school = SchoolMapper.toEntity(dto);
        privService.findSchoolLevelAndAssignToSchool(school, dto.getSchoolLevel());
        School schoolAfterSave = dao.saveOrUpdate(school);
        return SchoolMapper.toDto(schoolAfterSave);
    }

    @Override
    public SchoolResponseApiDto findSchoolById(Long id) {
        School school = dao.find(id).orElseThrow(() -> new EntityNotFoundException("School with given id " + id + " not exist"));
        return SchoolMapper.toDto(school);
    }

    @Override
    public void deleteSchoolById(Long id) {
        School school = dao.find(id).orElseThrow(() -> new EntityNotFoundException("School with given id " + id + " not exist"));
        dao.remove(school);
    }

    @Override
    public List<SchoolResponseApiDto> findAllSchools() {
        return dao.findAll()
                .stream()
                .map(SchoolMapper::toDto)
                .collect(Collectors.toList());
    }
}

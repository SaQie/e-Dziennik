package pl.edziennik.eDziennik.server.school.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.school.domain.School;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
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
        privService.findSchoolLevelAndAssignToSchool(school, dto.getIdSchoolLevel());
        School schoolAfterSave = dao.saveOrUpdate(school);
        return SchoolMapper.toDto(schoolAfterSave);
    }

    @Override
    public SchoolResponseApiDto findSchoolById(Long id) {
        School school = dao.get(id);
        return SchoolMapper.toDto(school);
    }

    @Override
    public void deleteSchoolById(Long id) {
        School school = dao.get(id);
        dao.remove(school);
    }

    @Override
    public List<SchoolResponseApiDto> findAllSchools() {
        return dao.findAll()
                .stream()
                .map(SchoolMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SchoolResponseApiDto updateSchool(Long id, SchoolRequestApiDto dto) {
        // TODO -> Walidacja
        Optional<School> optionalSchool = dao.find(id);
        if (optionalSchool.isPresent()){
            School school = optionalSchool.get();
            school.setCity(dto.getCity());
            school.setName(dto.getName());
            school.setNip(dto.getNip());
            school.setAdress(dto.getAddress());
            school.setRegon(dto.getRegon());
            school.setPhoneNumber(dto.getPhoneNumber());
            school.setPostalCode(dto.getPostalCode());
            return SchoolMapper.toDto(school);
        }
        School school = dao.saveOrUpdate(SchoolMapper.toEntity(dto));
        return SchoolMapper.toDto(school);
    }
}

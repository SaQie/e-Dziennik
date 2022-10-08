package pl.edziennik.eDziennik.server.school;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

    private final SchoolRepository repository;
    private final SchoolMapper mapper;
    private final SchoolPrivService privService;

    @Override
    public SchoolResponseApiDto createNewSchool(SchoolRequestApiDto dto) {
        School school = mapper.toEntity(dto);
        privService.findSchoolLevelAndAssignToSchool(school, dto.getSchoolLevel());
        School schoolAfterSave = repository.save(school);
        return mapper.toDto(schoolAfterSave);
    }

    @Override
    public SchoolResponseApiDto findSchoolById(Long id) {
        School school = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("School with given id " + id + " not exist"));
        return mapper.toDto(school);
    }

    @Override
    public void deleteSchoolById(Long id) {
        School school = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("School with given id " + id + " not exist"));
        repository.delete(school);
    }

    @Override
    public List<SchoolResponseApiDto> findAllSchools() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}

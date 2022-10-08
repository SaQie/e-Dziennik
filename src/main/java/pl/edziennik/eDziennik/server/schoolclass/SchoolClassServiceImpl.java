package pl.edziennik.eDziennik.server.schoolclass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class SchoolClassServiceImpl implements SchoolClassService{

    private final SchoolClassRepository repository;
    private final SchoolClassPrivService privService;
    private final SchoolClassMapper mapper;

    @Override
    public SchoolClassResponseApiDto createSchoolClass(SchoolClassRequestApiDto dto) {
        SchoolClass schoolClass = mapper.toEntity(dto);
        privService.checkSupervisingTeacherExist(dto.getSupervisingTeacherId(), schoolClass);
        privService.checkSchoolExist(dto.getSchool(), schoolClass);
        SchoolClass savedSchoolClass = repository.save(schoolClass);
        return mapper.toDto(savedSchoolClass);
    }

    @Override
    public SchoolClassResponseApiDto findSchoolClassById(Long id) {
        SchoolClass schoolClass = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("School class with given id " + id + " not exist"));
        return mapper.toDto(schoolClass);
    }

    @Override
    public void deleteSchoolClassById(Long id) {
        SchoolClass schoolClass = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("School class with given id " + id + " not exist"));
        repository.delete(schoolClass);
    }

    @Override
    public List<SchoolClassResponseApiDto> findAllSchoolClasses() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


}

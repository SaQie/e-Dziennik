package pl.edziennik.eDziennik.server.schoolclass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.dto.schoolclass.SchoolClassRequestDto;
import pl.edziennik.eDziennik.dto.schoolclass.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.dto.schoolclass.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.server.repositories.SchoolClassRepository;
import pl.edziennik.eDziennik.server.repositories.TeacherRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class SchoolClassServiceImpl implements SchoolClassService{

    private final SchoolClassRepository repository;
    private final SchoolClassPrivService privService;
    private final SchoolClassMapper mapper;

    @Override
    public SchoolClassResponseApiDto createSchoolClass(SchoolClassRequestDto dto) {
        SchoolClass schoolClass = mapper.toEntity(dto);
        privService.checkSupervisingTeacherExist(dto, schoolClass);
        SchoolClass savedSchoolClass = repository.save(schoolClass);
        return mapper.toDto(savedSchoolClass);
    }


}

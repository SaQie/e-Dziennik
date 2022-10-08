package pl.edziennik.eDziennik.server.subject;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.mapper.SubjectMapper;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.subjectclass.domain.SubjectClassLink;
import pl.edziennik.eDziennik.server.subjectclass.SubjectClassRepository;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

import java.util.List;

@Service
@AllArgsConstructor
class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository repository;
    private final SubjectClassRepository subjectClassRepository;
    private final SubjectPrivService privService;
    private final SubjectMapper mapper;

    @Override
    public SubjectResponseApiDto createNewSubject(SubjectRequestApiDto dto) {
        Subject subject = mapper.toEntity(dto);
        privService.checkTeacherExist(dto.getTeacher(), subject);
        Subject savedSubject = repository.save(subject);

        SubjectClassLink subjectClassLink = new SubjectClassLink();
        privService.checkSchoolClassExist(dto.getSchoolClass(), subjectClassLink);
        subjectClassLink.setSubject(savedSubject);

        subjectClassRepository.save(subjectClassLink);

        return mapper.toDto(savedSubject);
    }

    @Override
    public SubjectResponseApiDto findSubjectById(Long id) {
        return null;
    }

    @Override
    public void deleteSubjectById(Long id) {

    }

    @Override
    public List<SubjectResponseApiDto> findAllSubjects() {
        return null;
    }
}

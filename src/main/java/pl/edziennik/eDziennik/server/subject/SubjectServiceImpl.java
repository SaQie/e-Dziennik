package pl.edziennik.eDziennik.server.subject;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.mapper.SubjectMapper;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.subjectclass.domain.SubjectClassLink;
import pl.edziennik.eDziennik.server.subjectclass.SubjectClassRepository;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository repository;
    private final SubjectClassRepository subjectClassRepository;
    private final SubjectPrivService privService;

    @Override
    public SubjectResponseApiDto createNewSubject(SubjectRequestApiDto dto) {
        Subject subject = SubjectMapper.toEntity(dto);
        privService.checkTeacherExist(dto.getTeacher(), subject);
        Subject savedSubject = repository.save(subject);
        SubjectClassLink subjectClassLink = new SubjectClassLink();
        privService.checkSchoolClassExist(dto.getSchoolClass(), subjectClassLink);
        subjectClassLink.setSubject(savedSubject);
        subjectClassRepository.save(subjectClassLink);
        return SubjectMapper.toDto(savedSubject);
    }

    @Override
    public SubjectResponseApiDto findSubjectById(Long id) {
        Subject subject = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subject with given id " + id + " not exist"));
        return SubjectMapper.toDto(subject);
    }

    @Override
    public void deleteSubjectById(Long id) {
        Subject subject = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subject with given id " + id + " not exist"));
        repository.delete(subject);
    }

    @Override
    public List<SubjectResponseApiDto> findAllSubjects() {
        return repository.findAll()
                .stream()
                .map(SubjectMapper::toDto)
                .collect(Collectors.toList());
    }
}

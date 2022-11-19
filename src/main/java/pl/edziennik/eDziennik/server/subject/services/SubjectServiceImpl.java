package pl.edziennik.eDziennik.server.subject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.subject.dao.SubjectDao;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.mapper.SubjectMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class SubjectServiceImpl implements SubjectService{

    private final SubjectDao dao;
    private final SubjectPrivService privService;

    @Override
    @Transactional
    public SubjectResponseApiDto createNewSubject(SubjectRequestApiDto dto) {
        Subject subject = SubjectMapper.toEntity(dto);
        privService.checkTeacherExist(dto.getTeacher(), subject);
        Subject savedSubject = dao.saveOrUpdate(subject);
        return SubjectMapper.toDto(savedSubject);
    }

    @Override
    public SubjectResponseApiDto findSubjectById(Long id) {
        Subject subject = dao.find(id).orElseThrow(() -> new EntityNotFoundException("Subject with given id " + id + " not exist"));
        return SubjectMapper.toDto(subject);
    }

    @Override
    public void deleteSubjectById(Long id) {
        Subject subject = dao.find(id).orElseThrow(() -> new EntityNotFoundException("Subject with given id " + id + " not exist"));
        dao.remove(subject);
    }

    @Override
    public List<SubjectResponseApiDto> findAllSubjects() {
        return dao.findAll()
                .stream()
                .map(SubjectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubjectResponseApiDto updateSubject(Long id, SubjectRequestApiDto requestApiDto) {
        Optional<Subject> optionalSubject = dao.find(id);
        if (optionalSubject.isPresent()){
            Subject subject = optionalSubject.get();
            subject.setName(requestApiDto.getName());
            subject.setDescription(requestApiDto.getDescription());
            return SubjectMapper.toDto(subject);
        }
        Subject subject = dao.saveOrUpdate(SubjectMapper.toEntity(requestApiDto));
        return SubjectMapper.toDto(subject);
    }
}

package pl.edziennik.eDziennik.server.subject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.subject.dao.SubjectDao;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.mapper.SubjectMapper;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class SubjectServiceImpl implements SubjectService{

    private final SubjectDao dao;
    private final SubjectValidatorService validatorService;

    @Override
    @Transactional
    public SubjectResponseApiDto createNewSubject(SubjectRequestApiDto dto) {
        validatorService.valid(dto);
        Subject subject = mapToEntity(dto);
        Subject savedSubject = dao.saveOrUpdate(subject);
        return SubjectMapper.toDto(savedSubject);
    }



    @Override
    public SubjectResponseApiDto findSubjectById(Long id) {
        Subject subject = dao.get(id);
        return SubjectMapper.toDto(subject);
    }

    @Override
    public void deleteSubjectById(Long id) {
        Subject subject = dao.get(id);
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

    private Subject mapToEntity(SubjectRequestApiDto dto) {
        Subject subject = SubjectMapper.toEntity(dto);
        if (dto.getIdTeacher() != null){
            dao.findWithExecute(Teacher.class, dto.getIdTeacher(), subject::setTeacher);
        }
        SchoolClass schoolClass = dao.get(SchoolClass.class, dto.getIdSchoolClass());
        schoolClass.addSubject(subject);
        return subject;
    }
}

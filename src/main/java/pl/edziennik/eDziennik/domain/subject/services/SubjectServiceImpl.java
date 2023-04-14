package pl.edziennik.eDziennik.domain.subject.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.mapper.SubjectMapper;
import pl.edziennik.eDziennik.domain.subject.repository.SubjectRepository;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.server.basics.page.PageDto;
import pl.edziennik.eDziennik.server.basics.service.BaseService;

import java.util.Optional;

@Service
@AllArgsConstructor
class SubjectServiceImpl extends BaseService implements SubjectService {

    private final SubjectRepository repository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectValidatorService validatorService;

    @Override
    @Transactional
    public SubjectResponseApiDto createNewSubject(final SubjectRequestApiDto dto) {
        validatorService.valid(dto);
        Subject subject = mapToEntity(dto);
        Subject savedSubject = repository.save(subject);
        return SubjectMapper.toDto(savedSubject);
    }


    @Override
    public SubjectResponseApiDto findSubjectById(final SubjectId subjectId) {
        Subject subject = repository.findById(subjectId)
                .orElseThrow(notFoundException(subjectId.id(), Subject.class));
        return SubjectMapper.toDto(subject);
    }

    @Override
    public void deleteSubjectById(final SubjectId subjectId) {
        Subject subject = repository.findById(subjectId)
                .orElseThrow(notFoundException(subjectId.id(), Subject.class));
        repository.delete(subject);
    }

    @Override
    public PageDto<SubjectResponseApiDto> findAllSubjects(final Pageable pageable) {
        Page<SubjectResponseApiDto> page = repository.findAll(pageable).map(SubjectMapper::toDto);
        return PageDto.fromPage(page);
    }

    @Override
    @Transactional
    public SubjectResponseApiDto updateSubject(final SubjectId subjectId, final SubjectRequestApiDto dto) {
        Optional<Subject> optionalSubject = repository.findById(subjectId);
        if (optionalSubject.isPresent()) {
            validatorService.valid(dto);
            Subject subject = optionalSubject.get();
            subject.setName(dto.name());
            subject.setDescription(dto.description());
            return SubjectMapper.toDto(subject);
        }
        return createNewSubject(dto);
    }

    private Subject mapToEntity(SubjectRequestApiDto dto) {
        Subject subject = SubjectMapper.toEntity(dto);
        if (dto.teacherId() != null) {
            teacherRepository.findById(dto.teacherId()).ifPresentOrElse(subject::setTeacher,notFoundException(Teacher.class, dto.teacherId().id()));

        }
        SchoolClass schoolClass = schoolClassRepository.findById(dto.schoolClassId())
                .orElseThrow(notFoundException(dto.schoolClassId().id(), SchoolClass.class));
        schoolClass.addSubject(subject);
        return subject;
    }
}

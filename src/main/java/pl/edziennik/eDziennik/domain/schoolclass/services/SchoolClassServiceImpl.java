package pl.edziennik.eDziennik.domain.schoolclass.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.server.basics.page.PageDto;
import pl.edziennik.eDziennik.server.basics.service.BaseService;

import java.util.Optional;

@Service
@AllArgsConstructor
class SchoolClassServiceImpl extends BaseService implements SchoolClassService {

    private final SchoolClassValidatorService validatorService;
    private final SchoolClassRepository repository;
    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;

    @Override
    @Transactional
    public SchoolClassResponseApiDto createSchoolClass(SchoolClassRequestApiDto dto) {
        validatorService.valid(dto);
        SchoolClass schoolClass = mapToEntity(dto);
        SchoolClass savedSchoolClass = repository.save(schoolClass);
        return SchoolClassMapper.toDto(savedSchoolClass);
    }

    @Override
    public SchoolClassResponseApiDto findSchoolClassById(Long id) {
        SchoolClass schoolClass = repository.findById(id)
                .orElseThrow(notFoundException(id, SchoolClass.class));
        return SchoolClassMapper.toDto(schoolClass);
    }

    @Override
    public void deleteSchoolClassById(Long id) {
        SchoolClass schoolClass = repository.findById(id)
                .orElseThrow(notFoundException(id, SchoolClass.class));
        repository.delete(schoolClass);
    }

    @Override
    public PageDto<SchoolClassResponseApiDto> findAllSchoolClasses(Pageable pageable) {
        Page<SchoolClassResponseApiDto> page = repository.findAll(pageable).map(SchoolClassMapper::toDto);
        return PageDto.fromPage(page);
    }

    @Override
    @Transactional
    public SchoolClassResponseApiDto updateSchoolClass(Long id, SchoolClassRequestApiDto dto) {
        Optional<SchoolClass> schoolClassOptional = repository.findById(id);
        if (schoolClassOptional.isPresent()) {
            validatorService.valid(dto);
            // update school class data
            SchoolClass schoolClass = schoolClassOptional.get();

            schoolRepository.findById(dto.idSchool())
                    .ifPresentOrElse(schoolClass::setSchool, notFoundException(School.class, dto.idSchool()));

            if (dto.idClassTeacher() != null) {
                teacherRepository.findById(dto.idClassTeacher())
                        .ifPresentOrElse(schoolClass::setTeacher, notFoundException(Teacher.class, dto.idClassTeacher()));
            }

            schoolClass.setClassName(dto.className());
            return SchoolClassMapper.toDto(schoolClass);
        }

        return createSchoolClass(dto);

    }

    @Override
    public PageDto<SchoolClassResponseApiDto> findSchoolClassesBySchoolId(Pageable pageable, Long idSchool) {
        Page<SchoolClassResponseApiDto> page = repository.findSchoolClassesBySchoolId(pageable, idSchool).map(SchoolClassMapper::toDto);
        return PageDto.fromPage(page);
    }

    private SchoolClass mapToEntity(SchoolClassRequestApiDto dto) {
        SchoolClass schoolClass = SchoolClassMapper.toEntity(dto);
        schoolRepository.findById(dto.idSchool())
                .ifPresentOrElse(schoolClass::setSchool, notFoundException(School.class, dto.idSchool()));

        if (dto.idClassTeacher() != null) {
            teacherRepository.findById(dto.idClassTeacher())
                    .ifPresentOrElse(schoolClass::setTeacher, notFoundException(Teacher.class, dto.idClassTeacher()));
        }
        return schoolClass;
    }
}

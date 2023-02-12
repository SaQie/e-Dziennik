package pl.edziennik.eDziennik.domain.schoolclass.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.dao.SchoolClassDao;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassValidatorService validatorService;
    private final SchoolClassDao dao;

    @Override
    @Transactional
    public SchoolClassResponseApiDto createSchoolClass(SchoolClassRequestApiDto dto) {
        validatorService.valid(dto);
        SchoolClass schoolClass = mapToEntity(dto);
        SchoolClass savedSchoolClass = dao.saveOrUpdate(schoolClass);
        return SchoolClassMapper.toDto(savedSchoolClass);
    }

    @Override
    public SchoolClassResponseApiDto findSchoolClassById(Long id) {
        SchoolClass schoolClass = dao.get(id);
        return SchoolClassMapper.toDto(schoolClass);
    }

    @Override
    public void deleteSchoolClassById(Long id) {
        SchoolClass schoolClass = dao.get(id);
        dao.remove(schoolClass);
    }

    @Override
    public List<SchoolClassResponseApiDto> findAllSchoolClasses() {
        return dao.findAll()
                .stream()
                .map(SchoolClassMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SchoolClassResponseApiDto updateSchoolClass(Long id, SchoolClassRequestApiDto dto) {
        // TODO -> Walidacja
        Optional<SchoolClass> schoolClassOptional = dao.find(id);

        if (schoolClassOptional.isPresent()) {
            SchoolClass schoolClass = schoolClassOptional.get();
            schoolClass.setSchool(dao.get(School.class, dto.getIdSchool()));
            schoolClass.setClassName(dto.getClassName());
            schoolClass.setTeacher(dao.get(Teacher.class, dto.getIdClassTeacher()));
            return SchoolClassMapper.toDto(schoolClass);
        }

        SchoolClass schoolClass = dao.saveOrUpdate(SchoolClassMapper.toEntity(dto));
        return SchoolClassMapper.toDto(schoolClass);

    }

    @Override
    public List<SchoolClassResponseApiDto> findSchoolClassesBySchoolId(Long schoolId) {
        return dao.findSchoolClassesBySchoolId(schoolId).stream()
                .map(SchoolClassMapper::toDto)
                .toList();
    }

    private SchoolClass mapToEntity(SchoolClassRequestApiDto dto) {
        SchoolClass schoolClass = SchoolClassMapper.toEntity(dto);
        dao.findWithExecute(School.class, dto.getIdSchool(), schoolClass::setSchool);
        if (dto.getIdClassTeacher() != null) {
            dao.findWithExecute(Teacher.class, dto.getIdClassTeacher(), schoolClass::setTeacher);
        }
        return schoolClass;
    }
}

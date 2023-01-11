package pl.edziennik.eDziennik.server.schoolclass.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.dao.SchoolClassDao;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class SchoolClassServiceImpl implements SchoolClassService{

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

        if (schoolClassOptional.isPresent()){
            SchoolClass schoolClass = schoolClassOptional.get();
            schoolClass.setSchool(dao.get(School.class,dto.getIdSchool()));
            schoolClass.setClassName(dto.getClassName());
            schoolClass.setTeacher(dao.get(Teacher.class, dto.getIdSupervisingTeacher()));
            return SchoolClassMapper.toDto(schoolClass);
        }

        SchoolClass schoolClass = dao.saveOrUpdate(SchoolClassMapper.toEntity(dto));
        return SchoolClassMapper.toDto(schoolClass);

    }

    private SchoolClass mapToEntity(SchoolClassRequestApiDto dto) {
        SchoolClass schoolClass = SchoolClassMapper.toEntity(dto);
        dao.findWithExecute(School.class, dto.getIdSchool(), schoolClass::setSchool);
        if (dto.getIdSupervisingTeacher() != null) {
            dao.findWithExecute(Teacher.class, dto.getIdSupervisingTeacher(), schoolClass::setTeacher);
        }
        return schoolClass;
    }
}

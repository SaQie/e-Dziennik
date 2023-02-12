package pl.edziennik.eDziennik.domain.school.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.domain.school.dao.SchoolDao;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.domain.school.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.domain.schoollevel.domain.SchoolLevel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class SchoolServiceImpl implements SchoolService{

    private final SchoolDao dao;
    private final SchoolValidatorService validatorService;

    @Override
    @Transactional
    public SchoolResponseApiDto createNewSchool(SchoolRequestApiDto dto) {
        validatorService.valid(dto);
        School school = mapToEntity(dto);
        School schoolAfterSave = dao.saveOrUpdate(school);
        return SchoolMapper.toDto(schoolAfterSave);
    }

    @Override
    public SchoolResponseApiDto findSchoolById(Long id) {
        School school = dao.get(id);
        return SchoolMapper.toDto(school);
    }

    @Override
    public void deleteSchoolById(Long id) {
        School school = dao.get(id);
        dao.remove(school);
    }

    @Override
    public List<SchoolResponseApiDto> findAllSchools() {
        return dao.findAll()
                .stream()
                .map(SchoolMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SchoolResponseApiDto updateSchool(Long id, SchoolRequestApiDto dto) {
        // TODO -> Walidacja
        Optional<School> optionalSchool = dao.find(id);
        if (optionalSchool.isPresent()){
            School school = optionalSchool.get();
            school.setName(dto.getName());
            school.setNip(dto.getNip());
            school.setRegon(dto.getRegon());
            school.setPhoneNumber(dto.getPhoneNumber());
            Address address = AddressMapper.mapToAddress(dto.getAddress(), dto.getCity(), dto.getPostalCode());
            school.setAddress(address);
            return SchoolMapper.toDto(school);
        }
        SchoolLevel schoolLevel = dao.get(SchoolLevel.class, dto.getIdSchoolLevel());
        School school = SchoolMapper.toEntity(dto);
        school.setSchoolLevel(schoolLevel);
        return SchoolMapper.toDto(dao.saveOrUpdate(school));
    }

    private School mapToEntity(SchoolRequestApiDto dto){
        School school = SchoolMapper.toEntity(dto);
        dao.findWithExecute(SchoolLevel.class,dto.getIdSchoolLevel(), school::setSchoolLevel);
        return school;
    }
}

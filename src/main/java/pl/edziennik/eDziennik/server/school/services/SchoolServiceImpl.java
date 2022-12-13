package pl.edziennik.eDziennik.server.school.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.address.Address;
import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.school.domain.School;

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
        School school = validatorService.validateDtoAndMapToEntity(dto);
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
        School school = dao.saveOrUpdate(SchoolMapper.toEntity(dto));
        return SchoolMapper.toDto(school);
    }
}

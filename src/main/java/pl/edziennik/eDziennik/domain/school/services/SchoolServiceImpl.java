package pl.edziennik.eDziennik.domain.school.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.domain.school.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.schoollevel.domain.SchoolLevel;
import pl.edziennik.eDziennik.domain.schoollevel.repository.SchoolLevelRepository;
import pl.edziennik.eDziennik.server.basics.page.PageDto;
import pl.edziennik.eDziennik.server.basics.service.BaseService;

import java.util.Optional;

@Service
@AllArgsConstructor
class SchoolServiceImpl extends BaseService implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolValidatorService validatorService;
    private final SchoolLevelRepository schoolLevelRepository;

    @Override
    @Transactional
    public SchoolResponseApiDto createNewSchool(SchoolRequestApiDto dto) {
        validatorService.valid(dto);
        School school = mapToEntity(dto);
        School schoolAfterSave = schoolRepository.save(school);
        return SchoolMapper.toDto(schoolAfterSave);
    }

    @Override
    public SchoolResponseApiDto findSchoolById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(notFoundException(id, School.class));
        return SchoolMapper.toDto(school);
    }

    @Override
    public void deleteSchoolById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(notFoundException(id, School.class));
        schoolRepository.delete(school);
    }

    @Override
    public PageDto<SchoolResponseApiDto> findAllSchools(Pageable pageable) {
        Page<SchoolResponseApiDto> page = schoolRepository.findAll(pageable).map(SchoolMapper::toDto);
        return PageDto.fromPage(page);
    }

    @Override
    @Transactional
    public SchoolResponseApiDto updateSchool(Long id, SchoolRequestApiDto dto) {
        Optional<School> optionalSchool = schoolRepository.findById(id);
        if (optionalSchool.isPresent()) {
            validatorService.valid(dto);

            // update school data
            School school = optionalSchool.get();
            school.setName(dto.name());
            school.setNip(dto.nip());
            school.setRegon(dto.regon());
            school.setPhoneNumber(dto.phoneNumber());

            // update address data
            Address address = school.getAddress();
            address.setAddress(dto.address());
            address.setPostalCode(dto.postalCode());
            address.setCity(dto.city());

            return SchoolMapper.toDto(school);
        }
        return createNewSchool(dto);
    }

    private School mapToEntity(SchoolRequestApiDto dto) {
        School school = SchoolMapper.toEntity(dto);
        schoolLevelRepository.findById(dto.idSchoolLevel())
                .ifPresentOrElse(school::setSchoolLevel, notFoundException(SchoolLevel.class, dto.idSchoolLevel()));
        return school;
    }
}

package pl.edziennik.eDziennik.server.teacher.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.personinformation.PersonInformation;
import pl.edziennik.eDziennik.server.personinformation.PersonInformationMapper;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TeacherServiceImpl implements TeacherService{

    private final TeacherDao dao;
    private final PasswordEncoder passwordEncoder;
    private final TeacherValidatorService privService;

    @Override
    @Transactional
    public TeacherResponseApiDto register(TeacherRequestApiDto dto) {
        Teacher teacher = privService.validateDtoAndMapToEntity(dto);
        teacher.setPassword(passwordEncoder.encode(dto.getPassword()));
        return TeacherMapper.toDto(dao.saveOrUpdate(teacher));
    }


    @Override
    public TeacherResponseApiDto findTeacherById(Long id) {
        Teacher teacher = dao.get(id);
        return TeacherMapper.toDto(teacher);
    }

    @Override
    public void deleteTeacherById(Long id) {
        Teacher teacher = dao.get(id);
        dao.remove(teacher);
    }

    @Override
    public List<TeacherResponseApiDto> findAllTeachers() {
        return dao.findAll()
                .stream()
                .map(TeacherMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean updateTeacherLastLoginDate(String username) {
        Teacher teacher = dao.getByUsername(username);
        if (teacher != null){
            teacher.setLastLoginDate(LocalDateTime.now());
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public TeacherResponseApiDto updateTeacher(Long id, TeacherRequestApiDto requestApiDto) {
        Optional<Teacher> optionalTeacher = dao.find(id);
        if (optionalTeacher.isPresent()){
            privService.validateDto(requestApiDto);
            Teacher teacher = optionalTeacher.get();
            teacher.setAddress(AddressMapper.mapToAddress(requestApiDto.getAddress(),requestApiDto.getCity(),requestApiDto.getPostalCode()));
            teacher.setPhoneNumber(requestApiDto.getPhoneNumber());
            PersonInformation personInformation = PersonInformationMapper.mapToPersonInformation(requestApiDto.getFirstName(), requestApiDto.getLastName(), requestApiDto.getPesel());
            teacher.setPersonInformation(personInformation);
            return TeacherMapper.toDto(teacher);
        }
        return register(requestApiDto);
    }
}

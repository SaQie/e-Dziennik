package pl.edziennik.eDziennik.server.teacher.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.basics.BaseService;
import pl.edziennik.eDziennik.server.personinformation.PersonInformation;
import pl.edziennik.eDziennik.server.personinformation.PersonInformationMapper;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;
import pl.edziennik.eDziennik.server.user.domain.User;
import pl.edziennik.eDziennik.server.user.domain.UserMapper;
import pl.edziennik.eDziennik.server.user.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TeacherServiceImpl extends BaseService implements TeacherService {

    private final TeacherDao dao;

    private final TeacherValidatorService validatorService;
    private final UserService userService;

    @Override
    @Transactional
    public TeacherResponseApiDto register(TeacherRequestApiDto dto) {
        validatorService.valid(dto);
        User user = userService.createUser(UserMapper.toDto(dto));
        Teacher teacher = mapToEntity(dto);
        teacher.setUser(user);
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
    public TeacherResponseApiDto updateTeacher(Long id, TeacherRequestApiDto requestApiDto) {
        Optional<Teacher> optionalTeacher = dao.find(id);
        if (optionalTeacher.isPresent()) {
            validatorService.valid(requestApiDto);
            Teacher teacher = optionalTeacher.get();
            teacher.setAddress(AddressMapper.mapToAddress(requestApiDto.getAddress(), requestApiDto.getCity(), requestApiDto.getPostalCode()));
            teacher.setPhoneNumber(requestApiDto.getPhoneNumber());
            PersonInformation personInformation = PersonInformationMapper.mapToPersonInformation(requestApiDto.getFirstName(), requestApiDto.getLastName(), requestApiDto.getPesel());
            teacher.setPersonInformation(personInformation);
            return TeacherMapper.toDto(teacher);
        }
        return register(requestApiDto);
    }

    @Override
    public TeacherResponseApiDto getTeacherByUsername(String username) {
        Teacher teacher = dao.getByUsername(username);
        return teacher == null ? null : TeacherMapper.toDto(teacher);
    }

    private Teacher mapToEntity(TeacherRequestApiDto dto) {
        Teacher teacher = TeacherMapper.toEntity(dto);
        if (dto.getIdSchool() != null) {
            dao.findWithExecute(School.class, dto.getIdSchool(), teacher::setSchool);
        }
        return teacher;
    }
}

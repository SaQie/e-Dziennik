package pl.edziennik.eDziennik.domain.teacher.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.domain.address.services.AddressService;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.personinformation.dto.mapper.PersonInformationMapper;
import pl.edziennik.eDziennik.domain.personinformation.services.PersonInformationService;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.mapper.TeacherMapper;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.server.basics.page.PageDto;
import pl.edziennik.eDziennik.server.basics.service.BaseService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class TeacherServiceImpl extends BaseService implements TeacherService {

    private final TeacherRepository repository;
    private final SchoolRepository schoolRepository;

    private final TeacherValidatorService validatorService;
    private final UserService userService;

    private final PersonInformationService personInformationService;
    private final AddressService addressService;

    @Override
    @Transactional
    public TeacherResponseApiDto register(TeacherRequestApiDto dto) {
        validatorService.validate(dto);
        User user = userService.createUser(UserMapper.toDto(dto));
        Teacher teacher = mapToEntity(dto);
        teacher.setUser(user);
        return TeacherMapper.toDto(repository.save(teacher));
    }


    @Override
    public TeacherResponseApiDto findTeacherById(Long id) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(notFoundException(id, Teacher.class));
        return TeacherMapper.toDto(teacher);
    }

    @Override
    public void deleteTeacherById(Long id) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(notFoundException(id, Teacher.class));
        repository.delete(teacher);
    }

    @Override
    public PageDto<TeacherResponseApiDto> findAllTeachers(Pageable pageable) {
        Page<TeacherResponseApiDto> page = repository.findAll(pageable).map(TeacherMapper::toDto);
        return PageDto.fromPage(page);
    }


    @Override
    @Transactional
    public TeacherResponseApiDto updateTeacher(Long id, TeacherRequestApiDto dto) {
        Optional<Teacher> optionalTeacher = repository.findById(id);
        if (optionalTeacher.isPresent()) {
            validatorService.validate(dto);
            // update teacher data
            Teacher teacher = optionalTeacher.get();
            schoolRepository.findById(dto.idSchool())
                    .ifPresentOrElse(teacher::setSchool, notFoundException(School.class,dto.idSchool()));

            // update person information teacher data
            Long idPersonInformation = teacher.getPersonInformation().getId();
            personInformationService.update(idPersonInformation,
                    PersonInformationMapper.mapToPersonInformation(dto));

            // update address teacher data
            Long idAddress = teacher.getAddress().getId();
            addressService.update(idAddress, AddressMapper.mapToAddress(dto));

            // update user teacher data
            User user = teacher.getUser();
            userService.updateUser(user.getId(), UserMapper.toDto(dto));

            return TeacherMapper.toDto(teacher);
        }
        return register(dto);
    }

    @Override
    public TeacherResponseApiDto getTeacherByUsername(String username) {
        Teacher teacher = repository.getByUserUsername(username);
        return teacher == null ? null : TeacherMapper.toDto(teacher);
    }

    private Teacher mapToEntity(TeacherRequestApiDto dto) {
        Teacher teacher = TeacherMapper.toEntity(dto);
        if (dto.idSchool() != null) {
            schoolRepository.findById(dto.idSchool()).ifPresent(teacher::setSchool);
        }
        return teacher;
    }
}

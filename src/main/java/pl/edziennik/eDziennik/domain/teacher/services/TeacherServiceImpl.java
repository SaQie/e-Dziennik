package pl.edziennik.eDziennik.domain.teacher.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.address.domain.wrapper.AddressId;
import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.domain.address.services.AddressService;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.PhoneNumber;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.mapper.SubjectMapper;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.mapper.TeacherMapper;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.server.basic.page.PageDto;
import pl.edziennik.eDziennik.server.basic.service.BaseService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
class TeacherServiceImpl extends BaseService implements TeacherService {

    private final TeacherRepository repository;
    private final SchoolRepository schoolRepository;

    private final TeacherValidatorService validatorService;
    private final UserService userService;

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
    public TeacherResponseApiDto findTeacherById(final TeacherId teacherId) {
        Teacher teacher = repository.findById(teacherId)
                .orElseThrow(notFoundException(teacherId, Teacher.class));
        return TeacherMapper.toDto(teacher);
    }

    @Override
    public void deleteTeacherById(final TeacherId teacherId) {
        Teacher teacher = repository.findById(teacherId)
                .orElseThrow(notFoundException(teacherId, Teacher.class));
        repository.delete(teacher);
    }

    @Override
    public PageDto<TeacherResponseApiDto> findAllTeachers(final Pageable pageable) {
        Page<TeacherResponseApiDto> page = repository.findAll(pageable).map(TeacherMapper::toDto);
        return PageDto.fromPage(page);
    }


    @Override
    @Transactional
    public TeacherResponseApiDto updateTeacher(final TeacherId teacherId, final TeacherRequestApiDto dto) {
        Optional<Teacher> optionalTeacher = repository.findById(teacherId);
        if (optionalTeacher.isPresent()) {
            validatorService.validate(dto);
            // update teacher data
            Teacher teacher = optionalTeacher.get();
            schoolRepository.findById(dto.schoolId())
                    .ifPresentOrElse(teacher::setSchool, notFoundException(School.class, dto.schoolId()));

            // update person information teacher data
            teacher.setPersonInformation(PersonInformation.of(dto.firstName(),
                    dto.lastName(), PhoneNumber.of(dto.phoneNumber()), Pesel.of(dto.pesel())));

            // update address teacher data
            AddressId addressId = teacher.getAddress().getAddressId();
            addressService.update(addressId, AddressMapper.mapToAddress(dto));

            // update user teacher data
            User user = teacher.getUser();
            userService.updateUser(user.getUserId(), UserMapper.toDto(dto));

            return TeacherMapper.toDto(teacher);
        }
        return register(dto);
    }

    @Override
    public TeacherResponseApiDto getTeacherByUsername(String username) {
        Teacher teacher = repository.getByUserUsername(username);
        return teacher == null ? null : TeacherMapper.toDto(teacher);
    }

    @Override
    public List<SubjectResponseApiDto> getTeacherSubjects(TeacherId teacherId) {
        return repository.getTeacherSubjectList(teacherId).stream().map(SubjectMapper::toDto).toList();
    }

    private Teacher mapToEntity(TeacherRequestApiDto dto) {
        Teacher teacher = TeacherMapper.toEntity(dto);
        if (dto.schoolId() != null) {
            schoolRepository.findById(dto.schoolId()).ifPresent(teacher::setSchool);
        }
        return teacher;
    }
}

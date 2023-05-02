package pl.edziennik.eDziennik.domain.student.services;

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
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.settings.services.SettingsService;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.domain.student.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.domain.studentsubject.domain.StudentSubject;
import pl.edziennik.eDziennik.domain.studentsubject.repository.StudentSubjectRepository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
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
class StudentServiceImpl extends BaseService implements StudentService {

    private final StudentRepository repository;
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolRepository schoolRepository;
    private final StudentValidatorService validatorService;
    private final UserService userService;
    private final SettingsService settingsService;
    private final StudentSubjectRepository studentSubjectRepository;
    private final AddressService addressService;


    @Override
    @Transactional
    public StudentResponseApiDto register(final StudentRequestApiDto dto) {
        validatorService.valid(dto);
        Student student = mapToEntity(dto);
        User user = userService.createUser(UserMapper.toDto(dto));
        student.setUser(user);
        assignAllSchoolClassSubjectsToStudentIfNeeded(student, dto.schoolClassId());
        return StudentMapper.toDto(repository.save(student));
    }

    @Override
    public StudentResponseApiDto findStudentById(final StudentId studentId) {
        Student student = repository.findById(studentId)
                .orElseThrow(notFoundException(studentId, Student.class));
        return StudentMapper.toDto(student);
    }

    @Override
    public void deleteStudentById(final StudentId studentId) {
        Student student = repository.findById(studentId)
                .orElseThrow(notFoundException(studentId, Student.class));
        if (student.getParent() != null) {
            student.getParent().clearStudent();
        }
        repository.delete(student);
    }

    @Override
    public PageDto<StudentResponseApiDto> findAllStudents(final Pageable pageable) {
        Page<StudentResponseApiDto> page = repository.findAll(pageable).map(StudentMapper::toDto);
        return PageDto.fromPage(page);
    }

    @Override
    public StudentResponseApiDto getStudentByUsername(final String username) {
        Student student = repository.getByUserUsername(username);
        return student == null ? null : StudentMapper.toDto(student);
    }


    @Override
    @Transactional
    public StudentResponseApiDto updateStudent(final StudentId studentId, StudentRequestApiDto dto) {
        Optional<Student> optionalStudent = repository.findById(studentId);
        if (optionalStudent.isPresent()) {
            validatorService.valid(dto);

            // update student data
            Student student = optionalStudent.get();
            schoolRepository.findById(dto.schoolId())
                    .ifPresentOrElse(student::setSchool, notFoundException(School.class, dto.schoolId()));
            schoolClassRepository.findById(dto.schoolClassId())
                    .ifPresentOrElse(student::setSchoolClass, notFoundException(SchoolClass.class, dto.schoolId()));

            // update person information student data
            student.setPersonInformation(PersonInformation.of(dto.firstName(),
                    dto.lastName(),
                    PhoneNumber.of(dto.phoneNumber()),
                    Pesel.of(dto.pesel())));

            // update address student data
            AddressId addressId = student.getAddress().getAddressId();
            addressService.update(addressId, AddressMapper.mapToAddress(dto));

            // update user student data
            User user = student.getUser();
            userService.updateUser(user.getUserId(), UserMapper.toDto(dto));

            return StudentMapper.toDto(student);
        }
        return register(dto);
    }


    private Student mapToEntity(final StudentRequestApiDto dto) {
        Student student = StudentMapper.toEntity(dto);
        School school = schoolRepository.findById(dto.schoolId())
                .orElseThrow(notFoundException(dto.schoolId(), School.class));
        SchoolClass schoolClass = schoolClassRepository.findById(dto.schoolClassId())
                .orElseThrow(notFoundException(dto.schoolId(), SchoolClass.class));
        student.setSchool(school);
        student.setSchoolClass(schoolClass);
        return student;
    }

    private void assignAllSchoolClassSubjectsToStudentIfNeeded(Student student, SchoolClassId schoolClassId) {
        // This method will assign automatically all subjects assigned to school class to selected student if configuration is enabled
        if (settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD).booleanValue()) {
            SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                    .orElseThrow(notFoundException(schoolClassId, SchoolClass.class));
            if (!schoolClass.getSubjects().isEmpty()) {
                List<Subject> subjects = schoolClass.getSubjects();
                for (Subject subject : subjects) {
                    StudentSubject studentSubject = new StudentSubject();
                    studentSubject.setStudent(student);
                    studentSubject.setSubject(subject);
                    studentSubjectRepository.save(studentSubject);
                }
            }
        }
    }

}

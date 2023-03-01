package pl.edziennik.eDziennik.domain.student.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.personinformation.dto.mapper.PersonInformationMapper;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.settings.services.SettingsService;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.domain.student.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.domain.studentsubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.domain.studentsubject.domain.StudentSubject;
import pl.edziennik.eDziennik.domain.student.dao.StudentDao;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;
import pl.edziennik.eDziennik.domain.user.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class StudentServiceImpl implements StudentService {

    private final StudentDao dao;
    private final StudentValidatorService validatorService;
    private final UserService userService;
    private final SettingsService settingsService;
    private final StudentSubjectDao studentSubjectDao;


    @Override
    @Transactional
    public StudentResponseApiDto register(StudentRequestApiDto dto) {
        validatorService.valid(dto);
        Student student = mapToEntity(dto);
        User user = userService.createUser(UserMapper.toDto(dto));
        student.setUser(user);
        assignAllSchoolClassSubjectsToStudentIfNeeded(student, dto.getIdSchoolClass());
        return StudentMapper.toDto(dao.saveOrUpdate(student));
    }

    @Override
    public StudentResponseApiDto findStudentById(Long id) {
        Student student = dao.get(id);
        return StudentMapper.toDto(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        dao.remove(id);
    }

    @Override
    public List<StudentResponseApiDto> findAllStudents() {
        TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        return dao.findAll()
                .stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseApiDto getStudentByUsername(String username) {
        Student student = dao.getByUsername(username);
        return student == null ? null : StudentMapper.toDto(student);
    }


    @Override
    @Transactional
    public StudentResponseApiDto updateStudent(Long id, StudentRequestApiDto requestApiDto) {
        Optional<Student> optionalStudent = dao.find(id);
        if (optionalStudent.isPresent()) {
//            validatorService.valid(requestApiDto);
            Student student = optionalStudent.get();
            PersonInformation personInformation = PersonInformationMapper.mapToPersonInformation(requestApiDto.getFirstName(), requestApiDto.getLastName(), requestApiDto.getPesel());
            student.getUser().setAddress(AddressMapper.mapToAddress(requestApiDto.getAddress(), requestApiDto.getCity(), requestApiDto.getPostalCode()));
            student.setParentFirstName(requestApiDto.getParentFirstName());
            student.setParentLastName(requestApiDto.getParentLastName());
            student.setParentPhoneNumber(requestApiDto.getParentPhoneNumber());
            student.getUser().setUsername(requestApiDto.getUsername());
            student.getUser().setPersonInformation(personInformation);
            return StudentMapper.toDto(student);
        }
        return register(requestApiDto);
    }


    private Student mapToEntity(StudentRequestApiDto dto) {
        Student student = StudentMapper.toEntity(dto);
        School school = dao.get(School.class, dto.getIdSchool());
        SchoolClass schoolClass = dao.get(SchoolClass.class, dto.getIdSchoolClass());
        student.setSchool(school);
        student.setSchoolClass(schoolClass);
        return student;
    }

    private void assignAllSchoolClassSubjectsToStudentIfNeeded(Student student, Long idSchoolClass) {
        // This method will assign automatically all subjects assigned to school class to selected student if configuration is on
        if (settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD).isEnabled()) {
            SchoolClass schoolClass = dao.get(SchoolClass.class, idSchoolClass);
            if (!schoolClass.getSubjects().isEmpty()) {
                List<Subject> subjects = schoolClass.getSubjects();
                for (Subject subject : subjects) {
                    StudentSubject studentSubject = new StudentSubject();
                    studentSubject.setStudent(student);
                    studentSubject.setSubject(subject);
                    studentSubjectDao.saveOrUpdate(studentSubject);
                }
            }
        }
    }

}

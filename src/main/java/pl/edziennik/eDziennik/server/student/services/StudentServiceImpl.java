package pl.edziennik.eDziennik.server.student.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.personinformation.PersonInformation;
import pl.edziennik.eDziennik.server.personinformation.PersonInformationMapper;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.student.dao.StudentDao;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.user.domain.User;
import pl.edziennik.eDziennik.server.user.domain.UserMapper;
import pl.edziennik.eDziennik.server.user.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class StudentServiceImpl implements StudentService {

    private final StudentDao dao;
    private final StudentValidatorService validatorService;
    private final UserService userService;


    @Override
    @Transactional
    public StudentResponseApiDto register(StudentRequestApiDto dto) {
        validatorService.valid(dto);
        Student student = mapToEntity(dto);
        User user = userService.createUser(UserMapper.toDto(dto));
        student.setUser(user);
        /*

        Dodac cos w stylu takiego kodu:

        student.getSchoolClass.getSubjects
        for(Subject subject : subjects)
        studentSubjectService.assign..(new Dto(subject))
        Doda grupowo wszystkie przedmioty dla ucznia

         */
        return StudentMapper.toDto(dao.saveOrUpdate(student));
    }


    @Override
    public StudentResponseApiDto findStudentById(Long id) {
        Student student = dao.get(id);
        return StudentMapper.toDto(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        Student student = dao.get(id);
        dao.remove(student);
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
            validatorService.valid(requestApiDto);
            Student student = optionalStudent.get();
            PersonInformation personInformation = PersonInformationMapper.mapToPersonInformation(requestApiDto.getFirstName(), requestApiDto.getLastName(), requestApiDto.getPesel());
            student.setAddress(AddressMapper.mapToAddress(requestApiDto.getAddress(), requestApiDto.getCity(), requestApiDto.getPostalCode()));
            student.setParentFirstName(requestApiDto.getParentFirstName());
            student.setParentLastName(requestApiDto.getParentLastName());
            student.setParentPhoneNumber(requestApiDto.getParentPhoneNumber());
            student.getUser().setUsername(requestApiDto.getUsername());
            student.setPersonInformation(personInformation);
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
}

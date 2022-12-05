package pl.edziennik.eDziennik.server.student.services;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.student.dao.StudentDao;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.server.student.domain.Student;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class StudentServiceImpl implements StudentService{

    private final StudentDao dao;
    private final PasswordEncoder passwordEncoder;
    private final StudentPrivService privService;



    @Override
    @Transactional
    public StudentResponseApiDto register(StudentRequestApiDto dto) {
        Student student = privService.validateDtoAndMapToEntity(dto);
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
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
        return dao.findAll()
                .stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean updateStudentLastLoginDate(String username) {
        Student student = dao.getByUsername(username);
        if (student != null){
            student.setLastLoginDate(LocalDateTime.now());
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public StudentResponseApiDto updateStudent(Long id, StudentRequestApiDto requestApiDto) {
        Optional<Student> optionalStudent = dao.find(id);
        if (optionalStudent.isPresent()){
            privService.validateDto(requestApiDto);
            Student student = optionalStudent.get();
            student.setFirstName(requestApiDto.getFirstName());
            student.setLastName(requestApiDto.getLastName());
            student.setAddress(AddressMapper.mapToAddress(requestApiDto.getAddress(),requestApiDto.getCity(),requestApiDto.getPostalCode()));
            student.setPESEL(requestApiDto.getPesel());
            student.setParentFirstName(requestApiDto.getParentFirstName());
            student.setParentLastName(requestApiDto.getParentLastName());
            student.setParentPhoneNumber(requestApiDto.getParentPhoneNumber());
            student.setUsername(requestApiDto.getUsername());
            return StudentMapper.toDto(student);
        }
        return register(requestApiDto);
    }
}

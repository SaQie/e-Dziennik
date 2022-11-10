package pl.edziennik.eDziennik.server.student.services;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.student.dao.StudentDao;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.server.student.domain.Student;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        Student student = StudentMapper.toEntity(dto);
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        privService.checkSchoolExist(dto.getSchool(), student);
        privService.checkSchoolClassExist(dto.getSchoolClass(), student);
        Student savedStudent = dao.saveOrUpdate(student);
        return StudentMapper.toDto(savedStudent);
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
}

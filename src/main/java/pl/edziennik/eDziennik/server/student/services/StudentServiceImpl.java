package pl.edziennik.eDziennik.server.student.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.student.dao.StudentDao;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.server.student.domain.Student;

import javax.persistence.EntityNotFoundException;
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
        Student student = dao.find(id).orElseThrow(() -> new EntityNotFoundException("Student with given id " + id + " not exist"));
        return StudentMapper.toDto(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        Student student = dao.find(id).orElseThrow(() -> new EntityNotFoundException("Student with given id " + id + " not exist"));
        dao.remove(student);
    }

    @Override
    public List<StudentResponseApiDto> findAllStudents() {
        return dao.findAll()
                .stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }
}

package pl.edziennik.eDziennik.server.teacher.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;
import pl.edziennik.eDziennik.server.utils.ExecutionTimer;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TeacherServiceImpl implements TeacherService{

    // TODO -> Zrob cos takiego ze kazdy serwis musi dziedziczyc bo base serwis, tam dodaj metody add,update,get,validate
    // TODO -> Ta klasa bedzie klasa abstrakcyjna

    private final TeacherDao dao;
    private final PasswordEncoder passwordEncoder;
    private final TeacherPrivService privService;

    @Override
    @Transactional
    public TeacherResponseApiDto register(TeacherRequestApiDto dto) {
        Teacher teacher = TeacherMapper.toEntity(dto);
        teacher.setPassword(passwordEncoder.encode(dto.getPassword()));
        privService.checkSchoolExist(dto.getSchool(), teacher);
        privService.checkRoleExist(dto.getRole(), teacher);
        Teacher savedTeacher = dao.saveOrUpdate(teacher);
        return TeacherMapper.toDto(savedTeacher);
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
            Teacher teacher = optionalTeacher.get();
            teacher.setAdress(requestApiDto.getAdress());
            teacher.setFirstName(requestApiDto.getFirstName());
            teacher.setLastName(requestApiDto.getLastName());
            teacher.setCity(requestApiDto.getCity());
            teacher.setPhoneNumber(requestApiDto.getPhoneNumber());
            teacher.setPostalCode(requestApiDto.getPostalCode());
            teacher.setPESEL(requestApiDto.getPesel());
            return TeacherMapper.toDto(teacher);
        }
        Teacher teacher = dao.saveOrUpdate(TeacherMapper.toEntity(requestApiDto));
        return TeacherMapper.toDto(teacher);
    }
}

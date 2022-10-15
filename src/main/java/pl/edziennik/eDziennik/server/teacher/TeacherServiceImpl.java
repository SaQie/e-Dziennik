package pl.edziennik.eDziennik.server.teacher;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.role.RoleRepository;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TeacherServiceImpl implements TeacherService{

    private final TeacherDao dao;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TeacherPrivService privService;

    @Override
    public TeacherResponseApiDto register(TeacherRequestApiDto dto) {
        Teacher teacher = TeacherMapper.toEntity(dto);
        teacher.setPassword(passwordEncoder.encode(dto.getPassword()));
        privService.checkSchoolExist(dto.getSchool(), teacher);
        privService.checkRoleExist(dto.getRole(), teacher);
        teacher.setRole(roleRepository.findById(Role.RoleConst.ROLE_ADMIN.getId()).get());
        Teacher savedTeacher = dao.saveOrUpdate(teacher);
        return TeacherMapper.toDto(savedTeacher);
    }


    @Override
    public TeacherResponseApiDto findTeacherById(Long id) {
        Teacher teacher = dao.find(id).orElseThrow(() -> new EntityNotFoundException("Teacher with given id " + id + " not found."));
        return TeacherMapper.toDto(teacher);
    }

    @Override
    public void deleteTeacherById(Long id) {
        Teacher teacher = dao.find(id).orElseThrow(() -> new EntityNotFoundException("Teacher with given id " + id + " not exist"));
        dao.remove(teacher);
    }

    @Override
    public List<TeacherResponseApiDto> findAllTeachers() {
        return dao.findAll()
                .stream()
                .map(TeacherMapper::toDto)
                .collect(Collectors.toList());
    }
}

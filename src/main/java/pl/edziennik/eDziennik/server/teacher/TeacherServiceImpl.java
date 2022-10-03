package pl.edziennik.eDziennik.server.teacher;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.dto.teacher.TeacherRequestDto;
import pl.edziennik.eDziennik.dto.teacher.TeacherResponseApiDto;
import pl.edziennik.eDziennik.dto.teacher.mapper.TeacherMapper;
import pl.edziennik.eDziennik.server.role.Role;
import pl.edziennik.eDziennik.server.repositories.RoleRepository;
import pl.edziennik.eDziennik.server.repositories.SchoolRepository;
import pl.edziennik.eDziennik.server.repositories.TeacherRepository;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TeacherPrivService privService;
    private final TeacherMapper mapper;

    @Override
    public TeacherResponseApiDto register(TeacherRequestDto dto) {
        Teacher teacher = mapper.toEntity(dto);
        teacher.setPassword(passwordEncoder.encode(dto.getPassword()));
        privService.checkSchoolExist(dto, teacher);
        privService.checkRoleExist(dto, teacher);
        teacher.setRole(roleRepository.findById(Role.RoleConst.ROLE_ADMIN.id).get());
        Teacher savedTeacher = repository.save(teacher);
        return mapper.toDto(savedTeacher);
    }


    @Override
    public TeacherResponseApiDto findTeacherById(Long id) {
        Teacher teacher = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher with given id " + id + " not found."));
        return mapper.toDto(teacher);
    }

    @Override
    public void deleteTeacherById(Long id) {
        Teacher teacher = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher with given id " + id + " not exist"));
        repository.delete(teacher);
    }

    @Override
    public List<TeacherResponseApiDto> findAllTeachers() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}

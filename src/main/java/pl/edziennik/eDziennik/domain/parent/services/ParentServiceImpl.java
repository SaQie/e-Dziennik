package pl.edziennik.eDziennik.domain.parent.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.mapper.ParentMapper;
import pl.edziennik.eDziennik.domain.parent.repository.ParentRepository;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.server.basics.service.BaseService;

@Service
@AllArgsConstructor
class ParentServiceImpl extends BaseService implements ParentService {

    private final UserService userService;
    private final ParentRepository repository;
    private final StudentRepository studentRepository;
    private final ParentValidatorService validatorService;


    @Override
    @Transactional
    public ParentResponseApiDto register(ParentRequestApiDto dto) {
        validatorService.valid(dto);
        Parent parent = mapToEntity(dto);
        return ParentMapper.toDto(repository.save(parent));
    }

    @Override
    public ParentResponseApiDto findById(Long id) {
        Parent parent = repository.findById(id)
                .orElseThrow(notFoundException(id, Parent.class));
        return ParentMapper.toDto(parent);
    }

    @Override
    public void deleteById(Long id) {
        validatorService.checkParentHasStudent(id);
        repository.findById(id).ifPresent(repository::delete);
    }

    @Override
    public Page<ParentResponseApiDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ParentMapper::toDto);
    }

    private Parent mapToEntity(ParentRequestApiDto dto) {
        Parent parent = ParentMapper.toEntity(dto);
        User user = userService.createUser(UserMapper.toDto(dto));
        Student student = studentRepository.findById(dto.getIdStudent()).orElseThrow(notFoundException(dto.getIdStudent(), Student.class));
        parent.setStudent(student);
        parent.setUser(user);
        return parent;
    }
}

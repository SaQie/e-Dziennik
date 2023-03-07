package pl.edziennik.eDziennik.domain.parent.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.parent.dao.ParentDao;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.mapper.ParentMapper;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;
import pl.edziennik.eDziennik.server.basics.service.BaseService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class ParentServiceImpl extends BaseService implements ParentService {

    private final UserService userService;
    private final ParentDao dao;
    private final ParentValidatorService validatorService;


    @Override
    @Transactional
    public ParentResponseApiDto register(ParentRequestApiDto dto) {
        validatorService.valid(dto);
        Parent parent = mapToEntity(dto);
        return ParentMapper.toDto(dao.saveOrUpdate(parent));
    }

    @Override
    public ParentResponseApiDto findById(Long id) {
        Parent parent = dao.get(id);
        return ParentMapper.toDto(parent);
    }

    @Override
    public void deleteById(Long id) {
        dao.remove(id);
    }

    @Override
    public Page<List<ParentResponseApiDto>> findAll(PageRequest pageRequest) {
        return dao.findAll(pageRequest)
                .map(ParentMapper::toDto);
    }

    private Parent mapToEntity(ParentRequestApiDto dto) {
        Parent parent = ParentMapper.toEntity(dto);
        User user = userService.createUser(UserMapper.toDto(dto));
        Student student = dao.get(Student.class, dto.getIdStudent());
        parent.setStudent(student);
        parent.setUser(user);
        return parent;
    }
}

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
        User user = userService.createUser(UserMapper.toDto(dto));
        parent.setUser(user);
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
    public List<ParentResponseApiDto> findAll() {
        return dao.findAll()
                .stream()
                .map(ParentMapper::toDto)
                .toList();
    }

    private Parent mapToEntity(ParentRequestApiDto dto) {
        Parent parent = ParentMapper.toEntity(dto);
        dao.findWithExecute(Student.class, dto.getIdStudent(), parent::setStudent);
        return parent;

    }
}

package pl.edziennik.eDziennik.domain.parent.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.domain.address.services.AddressService;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.mapper.ParentMapper;
import pl.edziennik.eDziennik.domain.parent.repository.ParentRepository;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.personinformation.dto.mapper.PersonInformationMapper;
import pl.edziennik.eDziennik.domain.personinformation.services.PersonInformationService;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.server.basics.page.PageDto;
import pl.edziennik.eDziennik.server.basics.service.BaseService;

import java.util.Optional;

@Service
@AllArgsConstructor
class ParentServiceImpl extends BaseService implements ParentService {

    private final UserService userService;
    private final ParentRepository repository;
    private final StudentRepository studentRepository;
    private final ParentValidatorService validatorService;
    private final PersonInformationService personInformationService;
    private final AddressService addressService;


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
    @Transactional
    public ParentResponseApiDto update(Long id, ParentRequestApiDto dto) {
        Optional<Parent> parentOptional = repository.findById(id);
        if (parentOptional.isPresent()) {
            validatorService.valid(dto);
            // update parent data
            Parent parent = parentOptional.get();
            Student student = studentRepository.getReferenceById(dto.getIdStudent());
            parent.setStudent(student);

            // update user data
            userService.updateUser(parent.getUser().getId(), UserMapper.toDto(dto));

            // update person information parent data
            Long idPersonInformation = student.getPersonInformation().getId();
            personInformationService.update(idPersonInformation,
                    PersonInformationMapper.mapToPersonInformation(dto));

            // update address parent data
            Long idAddress = student.getAddress().getId();
            addressService.update(idAddress, AddressMapper.mapToAddress(dto));

            return ParentMapper.toDto(parent);
        }
        // register new parent if not exists
        return register(dto);
    }

    @Override
    public void deleteById(Long id) {
        validatorService.checkParentHasStudent(id);
        repository.findById(id).ifPresent(repository::delete);
    }

    @Override
    public PageDto<ParentResponseApiDto> findAll(Pageable pageable) {
        Page<ParentResponseApiDto> page = repository.findAll(pageable)
                .map(ParentMapper::toDto);
        return PageDto.fromPage(page);
    }

    private Parent mapToEntity(ParentRequestApiDto dto) {
        Parent parent = ParentMapper.toEntity(dto);
        User user = userService.createUser(UserMapper.toDto(dto));
        Student student = studentRepository.findById(dto.getIdStudent())
                .orElseThrow(notFoundException(dto.getIdStudent(), Student.class));
        parent.setStudent(student);
        parent.setUser(user);
        return parent;
    }
}

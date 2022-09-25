package pl.edziennik.eDziennik.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.dto.SchoolDto;
import pl.edziennik.eDziennik.entities.School;
import pl.edziennik.eDziennik.entities.SchoolLevelEnum;
import pl.edziennik.eDziennik.repositories.SchoolLevelRepository;
import pl.edziennik.eDziennik.repositories.SchoolRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class SchoolService {

    private final SchoolRepository repository;
    private final SchoolLevelRepository schoolLevelRepository;


    public SchoolDto createNewSchool(SchoolDto dto){
        School school = SchoolDto.dtoTo(dto);
        findSchoolLevelAndAssignToSchool(school);
        School schoolAfterSave = repository.save(school);
        return SchoolDto.dtoFrom(schoolAfterSave);
    }

    private void findSchoolLevelAndAssignToSchool(School school) {
        schoolLevelRepository.findById(SchoolLevelEnum.PRIMARY_SCHOOL.id)
                .ifPresent(school::setSchoolLevel);
    }

    public SchoolDto findSchoolById(Long id) {
        School school = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("School with given id " + id + " not exist"));
        return SchoolDto.dtoFrom(school);
    }
}

package pl.edziennik.eDziennik.server.school;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoollevel.SchoolLevelRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class SchoolPrivService {

    private final SchoolLevelRepository schoolLevelRepository;

    protected void findSchoolLevelAndAssignToSchool(School school, Long idSchoolLevel){
        schoolLevelRepository.findById(idSchoolLevel).ifPresentOrElse(school::setSchoolLevel, () -> {
            throw new EntityNotFoundException("School level with given id " + idSchoolLevel + " not exist");
        });
    }

}

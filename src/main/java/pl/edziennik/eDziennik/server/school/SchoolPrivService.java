package pl.edziennik.eDziennik.server.school;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoollevel.SchoolLevelDao;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class SchoolPrivService {

    private final SchoolLevelDao dao;

    protected void findSchoolLevelAndAssignToSchool(School school, Long idSchoolLevel){
        dao.find(idSchoolLevel).ifPresentOrElse(school::setSchoolLevel, () -> {
            throw new EntityNotFoundException("School level with given id " + idSchoolLevel + " not exist");
        });
    }

}

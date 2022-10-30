package pl.edziennik.eDziennik.server.school.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoollevel.dao.SchoolLevelDao;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class SchoolPrivService {

    private final SchoolLevelDao dao;

    protected void findSchoolLevelAndAssignToSchool(School school, Long idSchoolLevel){
        dao.findWithExistCheck(idSchoolLevel, school::setSchoolLevel);
    }

}

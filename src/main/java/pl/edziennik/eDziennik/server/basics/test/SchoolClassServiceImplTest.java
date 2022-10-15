package pl.edziennik.eDziennik.server.basics.test;

import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.BaseService;
import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.schoollevel.domain.SchoolLevel;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

@Service
public class SchoolClassServiceImplTest extends BaseService<SchoolClass> implements SchoolClassServiceTestI {

    private SchoolClassDAO dao;


    public SchoolClassServiceImplTest(IBaseDao<SchoolClass> dao) {
        super(dao);
        this.dao = (SchoolClassDAO) dao;
    }


}

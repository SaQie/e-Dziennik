package pl.edziennik.application.query.schoollevel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.view.schoollevel.SchoolLevelView;
import pl.edziennik.infrastructure.repository.schoollevel.SchoolLevelQueryRepository;

import java.util.List;

@Repository
@AllArgsConstructor
class SchoolLevelQueryDaoImpl implements SchoolLevelQueryDao {

    private final SchoolLevelQueryRepository schoolLevelQueryRepository;

    @Override
    public List<SchoolLevelView> getSchoolLevelsView() {
        return schoolLevelQueryRepository.findAll();
    }
}

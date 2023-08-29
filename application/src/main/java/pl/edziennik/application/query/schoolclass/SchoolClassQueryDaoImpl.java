package pl.edziennik.application.query.schoolclass;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.valueobject.base.Identifier;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.schoolclass.DetailedSchoolClassView;
import pl.edziennik.common.view.schoolclass.SchoolClassSummaryForSchoolView;
import pl.edziennik.common.view.schoolclass.config.SchoolClassConfigSummaryView;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassConfigurationQueryRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Repository
@AllArgsConstructor
class SchoolClassQueryDaoImpl implements SchoolClassQueryDao {

    private final ResourceCreator res;
    private final SchoolClassConfigurationQueryRepository schoolClassConfigurationQueryRepository;
    private final SchoolClassQueryRepository schoolClassQueryRepository;


    @Override
    public PageView<SchoolClassSummaryForSchoolView> getSchoolClassSummaryForSchoolView(SchoolId schoolId, Pageable pageable) {
        return PageView.fromPage(schoolClassQueryRepository.findAllWithPaginationForSchool(pageable, schoolId));
    }

    @Override
    public DetailedSchoolClassView getDetailedSchoolClassView(SchoolClassId schoolClassId) {
        DetailedSchoolClassView view = schoolClassQueryRepository.getSchoolClassView(schoolClassId);

        requireNonNull(view, schoolClassId);

        return view;
    }

    @Override
    public SchoolClassConfigSummaryView getSchoolClassConfigSummaryView(SchoolClassId schoolClassId) {

        return null;
    }

    private void requireNonNull(Object view, Identifier id) {
        if (view == null) {
            throw new BusinessException(res.notFoundError(id));
        }
    }
}

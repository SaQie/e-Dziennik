package pl.edziennik.application.query.school;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.cache.CacheValueConstants;
import pl.edziennik.common.valueobject.base.Identifier;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.school.DetailedSchoolView;
import pl.edziennik.common.view.school.SchoolSummaryView;
import pl.edziennik.common.view.school.config.SchoolConfigSummaryView;
import pl.edziennik.infrastructure.repository.school.SchoolConfigurationQueryRepository;
import pl.edziennik.infrastructure.repository.school.SchoolQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Repository
@AllArgsConstructor
class SchoolQueryDaoImpl implements SchoolQueryDao {

    private final ResourceCreator res;
    private final SchoolQueryRepository schoolQueryRepository;
    private final SchoolConfigurationQueryRepository schoolConfigurationQueryRepository;

    @Override
    public SchoolConfigSummaryView getSchoolConfigSummaryView(SchoolId schoolId) {
        SchoolConfigSummaryView view = schoolConfigurationQueryRepository.getSchoolConfigurationView(schoolId);

        requireNonNull(view, schoolId);

        return view;
    }

    @Override
    @Cacheable(value = CacheValueConstants.SCHOOLS, key = "#root.method.name")
    public PageView<SchoolSummaryView> getSchoolSummaryView(Pageable pageable) {
        return PageView.fromPage(schoolQueryRepository.findAllWithPagination(pageable));
    }

    @Override
    public DetailedSchoolView getDetailedSchoolView(SchoolId schoolId) {
        DetailedSchoolView view = schoolQueryRepository.getSchoolView(schoolId);

        requireNonNull(view, schoolId);

        return view;
    }


    private void requireNonNull(Object view, Identifier id) {
        if (view == null) {
            throw new BusinessException(res.notFoundError(id));
        }
    }

}

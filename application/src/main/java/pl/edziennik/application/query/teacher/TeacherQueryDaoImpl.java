package pl.edziennik.application.query.teacher;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.cache.CacheValueConstants;
import pl.edziennik.common.valueobject.base.Identifier;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacher.DetailedTeacherView;
import pl.edziennik.common.view.teacher.TeacherSubjectsSummaryView;
import pl.edziennik.common.view.teacher.TeacherSummaryView;
import pl.edziennik.infrastructure.repository.teacher.TeacherQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Repository
@AllArgsConstructor
class TeacherQueryDaoImpl implements TeacherQueryDao {

    private final ResourceCreator res;
    private final TeacherQueryRepository teacherQueryRepository;

    @Override
    @Cacheable(value = CacheValueConstants.TEACHERS, key = "#root.method.name")
    public PageView<TeacherSummaryView> getTeacherSummaryView(Pageable pageable) {
        return PageView.fromPage(teacherQueryRepository.findAllWithPagination(pageable));
    }

    @Override
    public PageView<TeacherSubjectsSummaryView> getTeacherSubjectsSummaryView(Pageable pageable, TeacherId teacherId) {
        return PageView.fromPage(teacherQueryRepository.getTeacherSubjectsSummaryWithPagination(pageable, teacherId));
    }

    @Override
    public DetailedTeacherView getDetailedTeacherView(TeacherId teacherId) {
        DetailedTeacherView view = teacherQueryRepository.getTeacherView(teacherId);

        requireNonNull(view, teacherId);

        return view;
    }

    private void requireNonNull(Object view, Identifier id) {
        if (view == null) {
            throw new BusinessException(res.notFoundError(id));
        }
    }

}

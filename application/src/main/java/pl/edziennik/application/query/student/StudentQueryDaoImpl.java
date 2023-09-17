package pl.edziennik.application.query.student;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.cache.CacheValueConstants;
import pl.edziennik.common.valueobject.base.Identifier;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.student.DetailedStudentView;
import pl.edziennik.common.view.student.StudentSummaryView;
import pl.edziennik.infrastructure.repository.student.StudentQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Repository
@AllArgsConstructor
class StudentQueryDaoImpl implements StudentQueryDao {

    private final ResourceCreator res;
    private final StudentQueryRepository studentQueryRepository;


    @Override
    @Cacheable(value = CacheValueConstants.STUDENTS, key = "#root.method.name")
    public PageView<StudentSummaryView> getStudentSummaryView(Pageable pageable) {
        return PageView.fromPage(studentQueryRepository.findAllWithPagination(pageable));
    }

    @Override
    public DetailedStudentView getDetailedStudentView(StudentId studentId) {
        DetailedStudentView view = studentQueryRepository.getStudentView(studentId);

        requireNonNull(view, studentId);

        return view;
    }

    private void requireNonNull(Object view, Identifier id) {
        if (view == null) {
            throw new BusinessException(res.notFoundError(id));
        }
    }
}

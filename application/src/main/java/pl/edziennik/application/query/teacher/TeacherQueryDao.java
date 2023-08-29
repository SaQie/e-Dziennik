package pl.edziennik.application.query.teacher;

import org.springframework.data.domain.Pageable;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacher.DetailedTeacherView;
import pl.edziennik.common.view.teacher.TeacherSubjectsSummaryView;
import pl.edziennik.common.view.teacher.TeacherSummaryView;

public interface TeacherQueryDao {

    PageView<TeacherSummaryView> getTeacherSummaryView(Pageable pageable);

    PageView<TeacherSubjectsSummaryView> getTeacherSubjectsSummaryView(Pageable pageable, TeacherId teacherId);

    DetailedTeacherView getDetailedTeacherView(TeacherId teacherId);


}

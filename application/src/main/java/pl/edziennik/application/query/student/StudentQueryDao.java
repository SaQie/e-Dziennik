package pl.edziennik.application.query.student;

import org.springframework.data.domain.Pageable;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.student.DetailedStudentView;
import pl.edziennik.common.view.student.StudentSummaryView;

public interface StudentQueryDao {

    PageView<StudentSummaryView> getStudentSummaryView(Pageable pageable);

    DetailedStudentView getDetailedStudentView(StudentId studentId);
}

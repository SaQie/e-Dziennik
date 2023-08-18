package pl.edziennik.application.query.subject.studentsgrades.all;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.view.grade.DetailedGradeView;
import pl.edziennik.common.view.grade.allsubjects.StudentAllSubjectsGradesHeaderView;
import pl.edziennik.common.view.grade.allsubjects.StudentAssignedSubjectsView;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.List;

@Component
@AllArgsConstructor
class GetAllSubjectsGradesOfSpecificStudentQueryHandler implements
        IQueryHandler<GetAllSubjectsGradesOfSpecificStudentQuery, StudentAllSubjectsGradesHeaderView> {

    private final GradeQueryRepository gradeQueryRepository;
    private final ResourceCreator res;

    @Override
    public StudentAllSubjectsGradesHeaderView handle(GetAllSubjectsGradesOfSpecificStudentQuery query) {
        // Get header
        StudentAllSubjectsGradesHeaderView header = gradeQueryRepository.getStudentAllSubjectsGradesHeaderView(query.studentId());

        // Get assigned subjects to student
        List<StudentAssignedSubjectsView> assignedSubjectsView = gradeQueryRepository.getStudentAssignedSubjectsView(query.studentId());

        // if subjects is empty (student doesn't have assigned subjects)
        if (assignedSubjectsView.isEmpty()) {
            throw new BusinessException(
                    res.notFoundError(GetAllSubjectsGradesOfSpecificStudentQuery.STUDENT_ID, query.studentId())
            );
        }

        // Get grades of student subject
        List<DetailedGradeView> detailedGradeView = gradeQueryRepository.getDetailedGradeView(query.studentId());

        // Join list of subjects with list of subject grades
        assignedSubjectsView = assignedSubjectsView.stream()
                .map(subject -> new StudentAssignedSubjectsView(subject, detailedGradeView.stream()
                        .filter(grade -> grade.studentSubjectId().equals(subject.studentSubjectId())).toList()
                )).toList();

        return new StudentAllSubjectsGradesHeaderView(header, assignedSubjectsView);
    }
}

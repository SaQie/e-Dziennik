package pl.edziennik.application.query.subject;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.cache.CacheValueConstants;
import pl.edziennik.common.valueobject.base.Identifier;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.view.grade.DetailedGradeView;
import pl.edziennik.common.view.grade.allsubjects.StudentAllSubjectsGradesHeaderView;
import pl.edziennik.common.view.grade.allsubjects.StudentAssignedSubjectsView;
import pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView;
import pl.edziennik.common.view.subject.DetailedSubjectView;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.List;

@Repository
@AllArgsConstructor
class SubjectQueryDaoImpl implements SubjectQueryDao {

    private final ResourceCreator res;
    private final SubjectQueryRepository subjectQueryRepository;
    private final GradeQueryRepository gradeQueryRepository;


    @Override
    public DetailedSubjectView getDetailedSubjectView(SubjectId subjectId) {
        DetailedSubjectView view = subjectQueryRepository.getSubjectView(subjectId);

        requireNonNull(view, subjectId);

        return view;
    }

    @Override
    public StudentGradesBySubjectView getStudentGradesBySubjectView(SubjectId subjectId, StudentId studentId) {
        StudentGradesBySubjectView view = gradeQueryRepository.getStudentGradesBySubjectView(subjectId, studentId);

        requireNonNull(view, studentId);

        List<DetailedGradeView> detailedStudentSubjectDto = gradeQueryRepository.getDetailedGradeView(subjectId, studentId);

        return new StudentGradesBySubjectView(view, detailedStudentSubjectDto);
    }

    @Override
    public StudentAllSubjectsGradesHeaderView getAllSubjectsGradesOfSpecificStudentView(StudentId studentId) {
        // Get header
        StudentAllSubjectsGradesHeaderView header = gradeQueryRepository.getStudentAllSubjectsGradesHeaderView(studentId);

        // Get assigned subjects to student
        List<StudentAssignedSubjectsView> assignedSubjectsView = gradeQueryRepository.getStudentAssignedSubjectsView(studentId);

        // if subjects is empty (student doesn't have assigned subjects)
        requireNonNull(assignedSubjectsView, studentId);

        // Get grades of student subject
        List<DetailedGradeView> detailedGradeView = gradeQueryRepository.getDetailedGradeView(studentId);

        // Join list of subjects with list of subject grades
        assignedSubjectsView = assignedSubjectsView.stream()
                .map(subject -> {
                    List<DetailedGradeView> gradesForSubject = detailedGradeView.stream()
                            .filter(grade -> grade.studentSubjectId().equals(subject.studentSubjectId()))
                            .toList();
                    return new StudentAssignedSubjectsView(subject, gradesForSubject);
                })
                .toList();

        return new StudentAllSubjectsGradesHeaderView(header, assignedSubjectsView);
    }

    @Override
    public List<StudentGradesBySubjectView> getAllStudentGradesBySubjectView(SubjectId subjectId) {
        List<StudentGradesBySubjectView> studentsBySubjectView = gradeQueryRepository.getStudentsGradesBySubjectView(subjectId);
        List<DetailedGradeView> detailedStudentSubjectView = gradeQueryRepository.getDetailedGradeView(subjectId);


        return studentsBySubjectView.stream()
                .map(view ->
                        new StudentGradesBySubjectView(view, detailedStudentSubjectView.stream()
                                .filter(grade -> grade.studentSubjectId().equals(view.studentSubjectId())).toList()))
                .toList();
    }


    private void requireNonNull(Object view, Identifier id) {
        if (view == null) {
            throw new BusinessException(res.notFoundError(id));
        }
    }
}

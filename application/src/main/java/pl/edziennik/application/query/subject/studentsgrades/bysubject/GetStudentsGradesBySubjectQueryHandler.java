package pl.edziennik.application.query.subject.studentsgrades.bysubject;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.grade.DetailedGradeView;
import pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;

import java.util.List;

@Component
@AllArgsConstructor
class GetStudentsGradesBySubjectQueryHandler implements IQueryHandler<GetStudentsGradesBySubjectQuery, List<StudentGradesBySubjectView>> {

    private final GradeQueryRepository gradeQueryRepository;

    @Override
    public List<StudentGradesBySubjectView> handle(GetStudentsGradesBySubjectQuery query) {
        List<StudentGradesBySubjectView> studentsBySubjectView = gradeQueryRepository.getStudentsGradesBySubjectView(query.subjectId());
        List<DetailedGradeView> detailedStudentSubjectView = gradeQueryRepository.getDetailedGradeView(query.subjectId());


        return studentsBySubjectView.stream()
                .map(view ->
                        new StudentGradesBySubjectView(view, detailedStudentSubjectView.stream()
                                .filter(grade -> grade.studentSubjectId().equals(view.studentSubjectId())).toList()))
                .toList();
    }
}

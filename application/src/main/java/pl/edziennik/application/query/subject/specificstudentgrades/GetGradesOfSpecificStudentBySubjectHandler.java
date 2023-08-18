package pl.edziennik.application.query.subject.specificstudentgrades;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.view.grade.DetailedGradeView;
import pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.List;

@Component
@AllArgsConstructor
class GetGradesOfSpecificStudentBySubjectHandler implements IQueryHandler<GetGradesOfSpecificStudentBySubjectQuery, StudentGradesBySubjectView> {

    private final GradeQueryRepository gradeQueryRepository;
    private final ResourceCreator res;


    @Override
    public StudentGradesBySubjectView handle(GetGradesOfSpecificStudentBySubjectQuery query) {
        StudentGradesBySubjectView view = gradeQueryRepository.getStudentGradesBySubjectView(query.subjectId(), query.studentId());

        if (view == null) {
            throw new BusinessException(
                    res.notFoundError(GetGradesOfSpecificStudentBySubjectQuery.STUDENT_ID, query.studentId())
            );
        }

        List<DetailedGradeView> detailedStudentSubjectDto = gradeQueryRepository.getDetailedGradeView(query.subjectId(), query.studentId());

        return new StudentGradesBySubjectView(view, detailedStudentSubjectDto);
    }
}

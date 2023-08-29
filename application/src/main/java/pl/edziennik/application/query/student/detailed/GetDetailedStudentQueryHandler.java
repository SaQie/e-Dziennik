package pl.edziennik.application.query.student.detailed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.student.DetailedStudentView;
import pl.edziennik.infrastructure.repository.student.StudentQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class GetDetailedStudentQueryHandler implements IQueryHandler<GetDetailedStudentQuery, DetailedStudentView> {

    private final StudentQueryRepository studentQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedStudentView handle(GetDetailedStudentQuery query) {
        DetailedStudentView detailedStudentView = studentQueryRepository.getStudentView(query.studentId());

        if (detailedStudentView == null) {
            throw new BusinessException(
                    res.notFoundError(GetDetailedStudentQuery.STUDENT_ID, query.studentId())
            );
        }

        return detailedStudentView;

    }
}

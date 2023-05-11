package pl.edziennik.application.query.student.detailedstudent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.student.DetailedStudentDto;
import pl.edziennik.infrastructure.repositories.student.StudentQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
class GetDetailedStudentQueryHandler implements IQueryHandler<GetDetailedStudentQuery, DetailedStudentDto> {

    private final StudentQueryRepository studentQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedStudentDto handle(GetDetailedStudentQuery query) {
        DetailedStudentDto detailedStudentDto = studentQueryRepository.getStudent(query.studentId());

        if (detailedStudentDto == null) {
            throw new BusinessException(
                    res.notFoundError(GetDetailedStudentQuery.STUDENT_ID, query.studentId())
            );
        }

        return detailedStudentDto;

    }
}

package pl.edziennik.application.query.student.getstudent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.student.StudentDto;
import pl.edziennik.infrastructure.repositories.student.StudentQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
class GetStudentQueryHandler implements IQueryHandler<GetStudentQuery, StudentDto> {

    private final StudentQueryRepository studentQueryRepository;
    private final ResourceCreator res;

    @Override
    public StudentDto handle(GetStudentQuery query) {
        StudentDto studentDto = studentQueryRepository.getStudent(query.studentId());

        if (studentDto == null) {
            throw new BusinessException(
                    res.notFoundError(GetStudentQuery.STUDENT_ID, query.studentId())
            );
        }

        return studentDto;

    }
}

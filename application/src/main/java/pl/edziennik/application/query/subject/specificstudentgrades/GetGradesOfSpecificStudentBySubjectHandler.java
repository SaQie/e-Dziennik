package pl.edziennik.application.query.subject.specificstudentgrades;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.grade.DetailedGradeDto;
import pl.edziennik.common.dto.grade.StudentGradesBySubjectDto;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.List;

@Component
@AllArgsConstructor
class GetGradesOfSpecificStudentBySubjectHandler implements IQueryHandler<GetGradesOfSpecificStudentBySubject, StudentGradesBySubjectDto> {

    private final GradeQueryRepository gradeQueryRepository;
    private final ResourceCreator res;


    @Override
    public StudentGradesBySubjectDto handle(GetGradesOfSpecificStudentBySubject query) {
        StudentGradesBySubjectDto dto = gradeQueryRepository.getStudentGradesBySubjectDto(query.subjectId(), query.studentId());

        if (dto == null) {
            throw new BusinessException(
                    res.notFoundError(GetGradesOfSpecificStudentBySubject.STUDENT_ID, query.studentId())
            );
        }

        List<DetailedGradeDto> detailedStudentSubjectDto = gradeQueryRepository.getDetailedStudentSubjectDto(query.subjectId(), query.studentId());

        return new StudentGradesBySubjectDto(dto, detailedStudentSubjectDto);
    }
}

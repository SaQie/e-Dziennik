package pl.edziennik.application.query.subject.specificstudentgrades;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.grade.DetailedGradeDto;
import pl.edziennik.common.dto.grade.bysubject.StudentGradesBySubjectDto;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.List;

@Component
@AllArgsConstructor
class GetGradesOfSpecificStudentBySubjectHandler implements IQueryHandler<GetGradesOfSpecificStudentBySubjectQuery, StudentGradesBySubjectDto> {

    private final GradeQueryRepository gradeQueryRepository;
    private final ResourceCreator res;


    @Override
    public StudentGradesBySubjectDto handle(GetGradesOfSpecificStudentBySubjectQuery query) {
        StudentGradesBySubjectDto dto = gradeQueryRepository.getStudentGradesBySubjectDto(query.subjectId(), query.studentId());

        if (dto == null) {
            throw new BusinessException(
                    res.notFoundError(GetGradesOfSpecificStudentBySubjectQuery.STUDENT_ID, query.studentId())
            );
        }

        List<DetailedGradeDto> detailedStudentSubjectDto = gradeQueryRepository.getDetailedGradeDto(query.subjectId(), query.studentId());

        return new StudentGradesBySubjectDto(dto, detailedStudentSubjectDto);
    }
}

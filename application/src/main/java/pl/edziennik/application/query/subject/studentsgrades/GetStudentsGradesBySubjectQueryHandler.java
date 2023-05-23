package pl.edziennik.application.query.subject.studentsgrades;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.grade.DetailedGradeDto;
import pl.edziennik.common.dto.grade.StudentGradesBySubjectDto;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;

import java.util.List;

@Component
@AllArgsConstructor
class GetStudentsGradesBySubjectQueryHandler implements IQueryHandler<GetStudentsGradesBySubjectQuery, List<StudentGradesBySubjectDto>> {

    private final GradeQueryRepository gradeQueryRepository;

    @Override
    public List<StudentGradesBySubjectDto> handle(GetStudentsGradesBySubjectQuery query) {
        List<StudentGradesBySubjectDto> studentsBySubjectDto = gradeQueryRepository.getStudentsGradesBySubjectDto(query.subjectId());
        List<DetailedGradeDto> detailedStudentSubjectDto = gradeQueryRepository.getDetailedStudentSubjectDto(query.subjectId());


        return studentsBySubjectDto.stream()
                .map(dto ->
                        new StudentGradesBySubjectDto(dto, detailedStudentSubjectDto.stream()
                                .filter(grade -> grade.studentSubjectId().equals(dto.studentSubjectId())).toList()))
                .toList();
    }
}

package pl.edziennik.application.query.teacher.detailedsubject;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.grade.DetailedStudentSubjectGradeDto;
import pl.edziennik.common.dto.grade.StudentGradesSummaryDto;
import pl.edziennik.common.dto.teacher.TeacherDetailedSubjectDto;
import pl.edziennik.infrastructure.repositories.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.repositories.teacher.TeacherQueryRepository;

import java.util.List;

@Component
@AllArgsConstructor
class GetTeacherDetailedSubjectQueryHandler implements IQueryHandler<GetTeacherDetailedSubjectQuery, TeacherDetailedSubjectDto> {

    private final TeacherQueryRepository teacherQueryRepository;
    private final GradeQueryRepository gradeQueryRepository;

    @Override
    public TeacherDetailedSubjectDto handle(GetTeacherDetailedSubjectQuery command) {
        // Walidacja, czy teacher jest przypisany do danego przedmiotu

        TeacherDetailedSubjectDto detailedSubjectDto = teacherQueryRepository.getTeacherDetailedSubjectDto(command.teacherId(), command.subjectId());
        List<StudentGradesSummaryDto> studentGradesSummaryDto = gradeQueryRepository.getStudentGradesSummaryDto(command.subjectId());
        List<DetailedStudentSubjectGradeDto> detailedStudentSubjectGradeDto = gradeQueryRepository.getDetailedStudentSubjectDto(command.subjectId());

        List<StudentGradesSummaryDto> resultList = studentGradesSummaryDto.stream()
                .map(gradesSummaryDto ->
                        new StudentGradesSummaryDto(gradesSummaryDto,
                                detailedStudentSubjectGradeDto.stream()
                                        .filter(studentSubjectGradeDto -> gradesSummaryDto.studentId().equals(studentSubjectGradeDto.studentId()))
                                        .toList()))
                .toList();


        return new TeacherDetailedSubjectDto(detailedSubjectDto, resultList);
    }
}

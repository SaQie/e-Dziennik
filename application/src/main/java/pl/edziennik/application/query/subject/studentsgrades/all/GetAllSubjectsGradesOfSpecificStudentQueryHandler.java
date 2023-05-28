package pl.edziennik.application.query.subject.studentsgrades.all;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.grade.DetailedGradeDto;
import pl.edziennik.common.dto.grade.allsubjects.StudentAllSubjectsGradesHeaderDto;
import pl.edziennik.common.dto.grade.allsubjects.StudentAssignedSubjectsDto;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.List;

@Component
@AllArgsConstructor
class GetAllSubjectsGradesOfSpecificStudentQueryHandler implements
        IQueryHandler<GetAllSubjectsGradesOfSpecificStudentQuery, StudentAllSubjectsGradesHeaderDto> {

    private final GradeQueryRepository gradeQueryRepository;
    private final ResourceCreator res;

    @Override
    public StudentAllSubjectsGradesHeaderDto handle(GetAllSubjectsGradesOfSpecificStudentQuery query) {
        // Get header
        StudentAllSubjectsGradesHeaderDto header = gradeQueryRepository.getStudentAllSubjectsGradesHeaderDto(query.studentId());

        // Get assigned subjects to student
        List<StudentAssignedSubjectsDto> assignedSubjectsDto = gradeQueryRepository.getStudentAssignedSubjectsDto(query.studentId());

        // if subjects is empty (student doesn't have assigned subjects)
        if (assignedSubjectsDto.isEmpty()) {
            throw new BusinessException(
                    res.notFoundError(GetAllSubjectsGradesOfSpecificStudentQuery.STUDENT_ID, query.studentId())
            );
        }

        // Get grades of student subject
        List<DetailedGradeDto> detailedGradeDto = gradeQueryRepository.getDetailedGradeDto(query.studentId());

        // Join list of subjects with list of subject grades
        assignedSubjectsDto = assignedSubjectsDto.stream()
                .map(subject -> new StudentAssignedSubjectsDto(subject, detailedGradeDto.stream()
                        .filter(grade -> grade.studentSubjectId().equals(subject.studentSubjectId())).toList()
                )).toList();

        return new StudentAllSubjectsGradesHeaderDto(header, assignedSubjectsDto);
    }
}

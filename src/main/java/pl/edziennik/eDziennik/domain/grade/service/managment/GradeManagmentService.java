package pl.edziennik.eDziennik.domain.grade.service.managment;

import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper.StudentSubjectSeparateId;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectDto;

public interface GradeManagmentService {


    StudentGradesInSubjectDto assignGradeToStudentSubject(final StudentSubjectSeparateId studentSubjectId, final GradeRequestApiDto dto);

    void deleteGradeFromStudentSubject(final StudentSubjectSeparateId studentSubjectId, final GradeId idGrade);

    StudentGradesInSubjectDto updateStudentSubjectGrade(final StudentSubjectSeparateId studentSubjectId, final GradeId idGrade, final GradeRequestApiDto requestApiDto);


}

package pl.edziennik.eDziennik.domain.grade.service.managment;

import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper.StudentSubjectSeparateId;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectResponseApiDto;

public interface GradeManagmentService {


    StudentGradesInSubjectResponseApiDto assignGradeToStudentSubject(final StudentSubjectSeparateId studentSubjectId, final GradeRequestApiDto dto);

    void deleteGradeFromStudentSubject(final StudentSubjectSeparateId studentSubjectId, final GradeId idGrade);

    StudentGradesInSubjectResponseApiDto updateStudentSubjectGrade(final StudentSubjectSeparateId studentSubjectId, final GradeId idGrade, final GradeRequestApiDto requestApiDto);


}

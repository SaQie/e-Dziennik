package pl.edziennik.eDziennik.domain.grade.service.managment;

import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.studensubject.dto.response.StudentGradesInSubjectDto;

public interface GradeManagmentService {


    StudentGradesInSubjectDto assignGradeToStudentSubject(final Long idStudent, final Long idSubject, final GradeRequestApiDto dto);

    void deleteGradeFromStudentSubject(final Long idStudent, final Long idSubject, final Long idGrade);

    StudentGradesInSubjectDto updateStudentSubjectGrade(final Long idStudent, final Long idSubject, final Long idGrade, final GradeRequestApiDto requestApiDto);


}

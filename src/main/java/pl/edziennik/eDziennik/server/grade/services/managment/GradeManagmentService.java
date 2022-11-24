package pl.edziennik.eDziennik.server.grade.services.managment;

import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectGradesResponseDto;

public interface GradeManagmentService {


    StudentSubjectGradesResponseDto assignGradeToStudentSubject(final Long idStudent, final Long idSubject, final GradeRequestApiDto dto);

    void deleteGradeFromStudentSubject(final Long idStudent, final Long idSubject, final Long idGrade);

    StudentSubjectGradesResponseDto updateStudentSubjectGrade(final Long idStudent, final Long idSubject, final Long idGrade, final GradeRequestApiDto requestApiDto);


}

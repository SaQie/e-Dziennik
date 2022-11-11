package pl.edziennik.eDziennik.server.studensubject.services;

import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectGradeRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectsResponseDto;

public interface StudentSubjectService {

    void assignStudentToSubject(final StudentSubjectRequestDto dto, final Long idStudent);

    void assignGradeToStudentSubject(final Long idStudent, final Long idSubject, final StudentSubjectGradeRequestDto dto, final String teacherName);

    StudentSubjectGradesResponseDto getStudentSubjectRatings(final Long idStudent, final Long idSubject);

    AllStudentSubjectGradesResponseDto getStudentAllSubjectsRatings(final Long idStudent);

    StudentSubjectsResponseDto getStudentSubjects(final Long idStudent);
}

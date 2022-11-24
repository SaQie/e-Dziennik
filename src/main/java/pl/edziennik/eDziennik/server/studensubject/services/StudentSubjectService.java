package pl.edziennik.eDziennik.server.studensubject.services;

import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentGradesInSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectsResponseDto;

public interface StudentSubjectService {

    StudentSubjectResponseDto assignStudentToSubject(final StudentSubjectRequestDto dto, final Long idStudent);

    StudentGradesInSubject getStudentSubjectGrades(final Long idStudent, final Long idSubject);

    AllStudentsGradesInSubjectsDto getStudentAllSubjectsGrades(final Long idStudent);

    StudentSubjectsResponseDto getStudentSubjects(final Long idStudent);

}

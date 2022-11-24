package pl.edziennik.eDziennik.server.studensubject.services;

import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectsResponseDto;

public interface StudentSubjectService {

    StudentSubjectResponseDto assignStudentToSubject(final StudentSubjectRequestDto dto, final Long idStudent);

    StudentSubjectGradesResponseDto getStudentSubjectGrades(final Long idStudent, final Long idSubject);

    AllStudentSubjectGradesResponseDto getStudentAllSubjectsGrades(final Long idStudent);

    StudentSubjectsResponseDto getStudentSubjects(final Long idStudent);

}

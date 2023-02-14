package pl.edziennik.eDziennik.domain.studentsubject.services;

import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectResponseDto;

public interface StudentSubjectService {

    StudentSubjectResponseDto assignStudentToSubject(final StudentSubjectRequestDto dto);

    StudentGradesInSubjectDto getStudentSubjectGrades(final Long idStudent, final Long idSubject);

    AllStudentsGradesInSubjectsDto getStudentAllSubjectsGrades(final Long idStudent);

    StudentSubjectsResponseDto getStudentSubjects(final Long idStudent);



}

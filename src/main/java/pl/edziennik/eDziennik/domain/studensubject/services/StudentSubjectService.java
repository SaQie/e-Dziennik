package pl.edziennik.eDziennik.domain.studensubject.services;

import pl.edziennik.eDziennik.domain.studensubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studensubject.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.domain.studensubject.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.domain.studensubject.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.domain.studensubject.dto.response.StudentSubjectResponseDto;

public interface StudentSubjectService {

    StudentSubjectResponseDto assignStudentToSubject(final StudentSubjectRequestDto dto);

    StudentGradesInSubjectDto getStudentSubjectGrades(final Long idStudent, final Long idSubject);

    AllStudentsGradesInSubjectsDto getStudentAllSubjectsGrades(final Long idStudent);

    StudentSubjectsResponseDto getStudentSubjects(final Long idStudent);



}

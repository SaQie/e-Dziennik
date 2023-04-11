package pl.edziennik.eDziennik.domain.studentsubject.services;

import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;

public interface StudentSubjectService {

    StudentSubjectResponseDto assignStudentToSubject(final StudentSubjectRequestDto dto);

    StudentGradesInSubjectDto getStudentSubjectGrades(final StudentId studentId, final SubjectId subjectId);

    AllStudentsGradesInSubjectsDto getStudentAllSubjectsGrades(final StudentId studentId);

    StudentSubjectsResponseDto getStudentSubjects(final StudentId studentId);



}

package pl.edziennik.eDziennik.domain.studentsubject.services;

import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectsDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;

import java.util.List;

public interface StudentSubjectService {

    StudentSubjectResponseDto assignStudentToSubject(final StudentSubjectRequestDto dto);

    StudentGradesInSubjectResponseApiDto getStudentSubjectGrades(final StudentId studentId, final SubjectId subjectId);

    StudentGradesInSubjectsDto getStudentAllSubjectsGrades(final StudentId studentId);

    StudentSubjectsResponseDto getStudentSubjects(final StudentId studentId);

    List<StudentGradesInSubjectResponseApiDto> getSpecificSubjectStudentsGrades(SubjectId subjectId);


}

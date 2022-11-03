package pl.edziennik.eDziennik.server.studensubject.services;

import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRatingRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectRatingResponseDto;

public interface StudentSubjectService {

    void assignStudentToSubject(final StudentSubjectRequestDto dto, final Long idStudent);

    void assignRatingToStudentSubject(final Long idStudent,final Long idSubject,final StudentSubjectRatingRequestDto dto, final String teacherName);


    StudentSubjectRatingResponseDto getStudentSubjectRatings(final Long idStudent, final Long idSubject);

    AllStudentSubjectGradesResponseDto getStudentAllSubjectsRatings(final Long idStudent);
}

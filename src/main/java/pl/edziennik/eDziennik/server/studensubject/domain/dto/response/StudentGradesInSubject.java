package pl.edziennik.eDziennik.server.studensubject.domain.dto.response;

import lombok.Getter;

@Getter
public class StudentGradesInSubject {

    private final Long id;

    private final String firstName;
    private final String lastName;

    private final SubjectGradesResponseDto subject;

    public StudentGradesInSubject(Long id, String firstName, String lastName, SubjectGradesResponseDto subjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subjects;
    }


}
